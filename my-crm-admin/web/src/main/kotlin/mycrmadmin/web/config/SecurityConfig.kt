package mycrmadmin.web.config

import mycrmadmin.web.security.CustomSavedRequestAwareAuthenticationSuccessHandler
import mycrmadmin.web.security.oauth2.CustomAuthorizationRequestResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder
import org.springframework.security.oauth2.client.oidc.authentication.OidcIdTokenDecoderFactory
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.oidc.OidcScopes
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm
import org.springframework.security.oauth2.jwt.JwtDecoderFactory
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder

/**
 * セキュリティーコンフィグ
 */
@Configuration
class SecurityConfig {
    /**
     * セキュリティフィルター
     */
    @Bean
    fun filterChain(
        http: HttpSecurity,
        clientRegistrationRepository: ClientRegistrationRepository,
    ): SecurityFilterChain {
        http {
            authorizeRequests {
                authorize(anyRequest, authenticated)
            }
            exceptionHandling {
                // 未認証の状態で認証が必要な URL にアクセスすると、ログイン画面へリダイレクトするレスポンスになる。
                // リダイレクトではなく 401 を返す場合は、authenticationEntryPoint で変更する。
                authenticationEntryPoint = HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
            }
            oauth2Login {
                loginPage = "/login/oauth2"
                authenticationSuccessHandler = CustomSavedRequestAwareAuthenticationSuccessHandler()
                authenticationFailureHandler = AuthenticationFailureHandler { request, response, exception ->
                    val error = when (exception) {
                        is OAuth2AuthenticationException -> exception.error.errorCode
                        else -> "unknown"
                    }
                    val url = UriComponentsBuilder.fromPath("/login/error")
                        .queryParam("error", error)
                        .build()
                        .toString()
                    val redirectStrategy = DefaultRedirectStrategy()
                    redirectStrategy.sendRedirect(request, response, url)
                }
                authorizationEndpoint {
                    baseUri = "/login/oauth2"
                    authorizationRequestResolver =
                        CustomAuthorizationRequestResolver(clientRegistrationRepository, "/login/oauth2")
                }
            }
        }

        return http.build()
    }

    /**
     * ID トークンデコーダーファクトリー
     */
    @Bean
    fun idTokenDecoderFactory(): JwtDecoderFactory<ClientRegistration> {
        val idTokenDecoderFactory = OidcIdTokenDecoderFactory()
        idTokenDecoderFactory.setJwsAlgorithmResolver { _ -> SignatureAlgorithm.ES256 }
        return idTokenDecoderFactory
    }

    /**
     * クライアントレジストレーションリポジトリ
     */
    @Bean
    fun clientRegistrationRepository(): ClientRegistrationRepository {
        return InMemoryClientRegistrationRepository(
            ClientRegistration.withRegistrationId("127.0.0.1")
                .clientId("00000000-0000-0000-0000-000000000003")
                .clientSecret("secret")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/login-client")
                .scope(OidcScopes.OPENID)
                .authorizationUri("http://127.0.0.1:8091/auth")
                .tokenUri("http://127.0.0.1:8091/token")
                .jwkSetUri("http://127.0.0.1:8091/jwks")
                .build(),
        )
    }

    /**
     * 認可クライアントマネージャー
     */
    @Bean
    fun authorizedClientManager(
        clientRegistrationRepository: ClientRegistrationRepository,
        authorizedClientRepository: OAuth2AuthorizedClientRepository,
    ): OAuth2AuthorizedClientManager {
        val authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
            .authorizationCode()
            .build()
        val authorizedClientManager =
            DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository)
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider)
        return authorizedClientManager
    }

    /**
     * リソースサーバーへの HTTP リクエストで使用する WebClient
     */
    @Bean
    fun webClient(authorizedClientManager: OAuth2AuthorizedClientManager): WebClient {
        val oauth2Client = ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager)
        return WebClient.builder()
            .apply(oauth2Client.oauth2Configuration())
            .build()
    }
}
