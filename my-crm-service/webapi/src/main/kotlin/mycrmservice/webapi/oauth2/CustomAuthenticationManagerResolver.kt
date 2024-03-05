package mycrmservice.webapi.oauth2

import jakarta.servlet.http.HttpServletRequest
import mycrmservice.webapi.authorization.ActorType
import mycrmservice.core.entity.IssuerType
import mycrmservice.core.repository.TokenIntrospectionClientConfigRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationManagerResolver
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames
import org.springframework.security.oauth2.server.resource.authentication.OpaqueTokenAuthenticationProvider
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector
import org.springframework.stereotype.Component
import java.time.Instant

/**
 * カスタム認証マネージャーリゾルバー
 */
@Component
class CustomAuthenticationManagerResolver(
    private val tokenIntrospectionClientConfigRepository: TokenIntrospectionClientConfigRepository,
) : AuthenticationManagerResolver<HttpServletRequest> {
    override fun resolve(context: HttpServletRequest): AuthenticationManager? {
        val clientConfig =
            tokenIntrospectionClientConfigRepository.findByResourceServerDomainName(context.serverName)
                ?: return null

        val client = NimbusOpaqueTokenIntrospector(
            clientConfig.introspectionEndpoint,
            clientConfig.clientId,
            clientConfig.clientSecret,
        )

        val provider = OpaqueTokenAuthenticationProvider(client)
        provider.setAuthenticationConverter { introspectedToken, authenticatedPrincipal ->
            val iat = authenticatedPrincipal.getAttribute<Instant>(OAuth2TokenIntrospectionClaimNames.IAT)
            val exp = authenticatedPrincipal.getAttribute<Instant>(OAuth2TokenIntrospectionClaimNames.EXP)
            val accessToken = OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER,
                introspectedToken,
                iat,
                exp,
            )
            val hasSubjectClaim = authenticatedPrincipal.attributes.contains(
                OAuth2TokenIntrospectionClaimNames.SUB,
            )
            val actorType = when (clientConfig.issuerType) {
                IssuerType.SERVICE -> {
                    if (hasSubjectClaim) {
                        ActorType.SERVICE_USER
                    } else {
                        ActorType.SERVICE_APPLICATION
                    }
                }

                IssuerType.SYSTEM -> {
                    if (hasSubjectClaim) {
                        ActorType.SYSTEM_USER
                    } else {
                        ActorType.SYSTEM_APPLICATION
                    }
                }
            }
            CustomBearerTokenAuthentication(
                authenticatedPrincipal,
                accessToken,
                authenticatedPrincipal.authorities,
                clientConfig.tenantId,
                actorType,
            )
        }

        return ProviderManager(provider)
    }
}
