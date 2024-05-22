package mycrmauth.web.config

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import myoidcprovider.ktor.routing.authorizationEndpoint
import myoidcprovider.ktor.routing.authorizationServerMetadataEndpoint
import myoidcprovider.ktor.routing.introspectionEndpoint
import myoidcprovider.ktor.routing.jwksEndpoint
import myoidcprovider.ktor.routing.openIdProviderConfigurationEndpoint
import myoidcprovider.ktor.routing.revocationEndpoint
import myoidcprovider.ktor.routing.tokenEndpoint

/**
 * oidc-provider ルーティングの設定
 */
fun Application.configureOidcProviderRouting() {
    routing {
        authorizationServerMetadataEndpoint()
        openIdProviderConfigurationEndpoint()
        jwksEndpoint()
        authorizationEndpoint()
        tokenEndpoint()
        introspectionEndpoint()
        revocationEndpoint()
    }
}
