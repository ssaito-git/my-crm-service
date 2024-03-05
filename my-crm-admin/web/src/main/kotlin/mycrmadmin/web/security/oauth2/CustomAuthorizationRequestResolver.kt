package mycrmadmin.web.security.oauth2

import jakarta.servlet.http.HttpServletRequest
import mycrmadmin.web.security.CustomHttpSessionRequestCache
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest

/**
 * カスタム認可リクエストリゾルバー
 */
class CustomAuthorizationRequestResolver(
    clientRegistrationRepository: ClientRegistrationRepository,
    private val authorizationRequestBaseUri: String,
) : OAuth2AuthorizationRequestResolver {
    private val defaultResolver: OAuth2AuthorizationRequestResolver

    init {
        defaultResolver =
            DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, authorizationRequestBaseUri)
        defaultResolver.setAuthorizationRequestCustomizer(OAuth2AuthorizationRequestCustomizers.withPkce())
    }

    override fun resolve(request: HttpServletRequest?): OAuth2AuthorizationRequest? {
        if (request?.requestURI != authorizationRequestBaseUri) {
            return null
        }

        // TODO: リクエストをもとに登録されたクライアントの ID を取得する

        CustomHttpSessionRequestCache().saveRequest(request)

        return resolve(request, "127.0.0.1")
    }

    override fun resolve(request: HttpServletRequest, clientRegistrationId: String): OAuth2AuthorizationRequest? {
        return defaultResolver.resolve(request, clientRegistrationId)
    }
}
