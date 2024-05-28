package mycrmauth.web.config

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.Curve
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.gen.ECKeyGenerator
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.response.respondRedirect
import io.ktor.server.response.respondText
import myoidcprovider.core.Provider
import myoidcprovider.core.client.ClientConfig
import myoidcprovider.core.client.ClientType
import myoidcprovider.core.client.authentication.ClientAuthenticationManager
import myoidcprovider.core.client.authentication.ClientSecretBasicAuthenticator
import myoidcprovider.core.client.authentication.ClientSecretPostAuthenticator
import myoidcprovider.core.config.Config
import myoidcprovider.core.issuer.IssuerConfig
import myoidcprovider.core.jwk.JWKConfig
import myoidcprovider.core.metadata.GrantType
import myoidcprovider.core.metadata.PKCECodeChallengeMethod
import myoidcprovider.core.metadata.ResponseType
import myoidcprovider.core.request.authorization.AuthenticationErrorResponse
import myoidcprovider.core.request.authorization.AuthorizationErrorResponse
import myoidcprovider.core.request.authorization.InvalidClient
import myoidcprovider.core.request.authorization.InvalidRedirectUri
import myoidcprovider.core.storage.AccessTokenStorageMemory
import myoidcprovider.core.storage.AuthorizationCodeStorageMemory
import myoidcprovider.core.storage.AuthorizationRequestDataStorageMemory
import myoidcprovider.core.storage.ClientConfigStorageMemory
import myoidcprovider.core.storage.IssuerConfigStorageMemory
import myoidcprovider.core.storage.JWKConfigStorageMemory
import myoidcprovider.core.storage.RefreshTokenStorageMemory
import myoidcprovider.core.storage.UserClaimSetStorageMemory
import myoidcprovider.ktor.plugin.OidcProvider
import myoidcprovider.ktor.util.toRedirectUrl
import java.util.UUID
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

/**
 * oidc-provider の設定
 */
fun Application.configureOidcProvider() {
    val serviceIssuer = "http://localhost:8091"
    val systemIssuer = "http://127.0.0.1:8091"

    val issuerConfigStorage = IssuerConfigStorageMemory(
        mapOf(
            serviceIssuer to IssuerConfig(
                serviceIssuer,
                listOf("openid", "offline_access", "user:read", "user:write", "product:read", "product:write"),
                listOf(ResponseType.CODE, ResponseType.ID_TOKEN),
                listOf(GrantType.AUTHORIZATION_CODE, GrantType.CLIENT_CREDENTIALS),
                listOf(PKCECodeChallengeMethod.S256),
                true,
                10.minutes.inWholeSeconds,
                5.minutes.inWholeSeconds,
                5.minutes.inWholeSeconds,
                30.days.inWholeSeconds,
                5.minutes.inWholeSeconds,
            ),
            systemIssuer to IssuerConfig(
                systemIssuer,
                listOf("openid"),
                listOf(ResponseType.CODE, ResponseType.ID_TOKEN),
                listOf(GrantType.AUTHORIZATION_CODE, GrantType.CLIENT_CREDENTIALS),
                listOf(PKCECodeChallengeMethod.S256),
                true,
                10.minutes.inWholeSeconds,
                5.minutes.inWholeSeconds,
                5.minutes.inWholeSeconds,
                30.days.inWholeSeconds,
                5.minutes.inWholeSeconds,
            ),
        ),
    )

    val jwkConfigStorageMemory = JWKConfigStorageMemory(
        mapOf(
            serviceIssuer to mapOf(
                ("foo-" + UUID.randomUUID().toString()).let {
                    it to JWKConfig(
                        true,
                        JWSAlgorithm.ES256,
                        ECKeyGenerator(Curve.P_256)
                            .keyUse(KeyUse.SIGNATURE)
                            .keyID(it)
                            .generate(),
                    )
                },
                ("bar-" + UUID.randomUUID().toString()).let {
                    it to JWKConfig(
                        true,
                        JWSAlgorithm.ES256,
                        ECKeyGenerator(Curve.P_256)
                            .keyUse(KeyUse.SIGNATURE)
                            .keyID(it)
                            .generate(),
                    )
                },
            ),
            systemIssuer to mapOf(
                ("baz-" + UUID.randomUUID().toString()).let {
                    it to JWKConfig(
                        true,
                        JWSAlgorithm.ES256,
                        ECKeyGenerator(Curve.P_256)
                            .keyUse(KeyUse.SIGNATURE)
                            .keyID(it)
                            .generate(),
                    )
                },
                ("qux-" + UUID.randomUUID().toString()).let {
                    it to JWKConfig(
                        true,
                        JWSAlgorithm.ES256,
                        ECKeyGenerator(Curve.P_256)
                            .keyUse(KeyUse.SIGNATURE)
                            .keyID(it)
                            .generate(),
                    )
                },
            ),
        ),
    )

    val clientConfigStorage = ClientConfigStorageMemory(
        mapOf(
            serviceIssuer to mapOf(
                // ID プロバイダー
                "00000000-0000-0000-0000-000000000000" to ClientConfig(
                    "00000000-0000-0000-0000-000000000000",
                    "id provider",
                    "secret",
                    ClientType.CONFIDENTIAL,
                    emptyList(),
                    emptyList(),
                    emptyList(),
                    null,
                    null,
                    null,
                    null,
                    null,
                ),
                "00000000-0000-0000-0000-000000000001" to ClientConfig(
                    "00000000-0000-0000-0000-000000000001",
                    "foo client",
                    "secret",
                    ClientType.CONFIDENTIAL,
                    listOf("openid", "user:read", "user:write", "offline_access"),
                    listOf(GrantType.AUTHORIZATION_CODE, GrantType.CLIENT_CREDENTIALS),
                    listOf("http://localhost/cb"),
                    null,
                    null,
                    null,
                    null,
                    null,
                ),
                // リソースサーバー
                "00000000-0000-0000-0000-000000000002" to ClientConfig(
                    "00000000-0000-0000-0000-000000000002",
                    "resource server",
                    "secret",
                    ClientType.CONFIDENTIAL,
                    emptyList(),
                    emptyList(),
                    emptyList(),
                    null,
                    null,
                    null,
                    null,
                    null,
                ),
                "00000000-0000-0000-0000-000000000003" to ClientConfig(
                    "00000000-0000-0000-0000-000000000003",
                    "foo application",
                    "secret",
                    ClientType.CONFIDENTIAL,
                    listOf("user:read", "user:write", "product:read", "product:write"),
                    listOf(GrantType.CLIENT_CREDENTIALS),
                    emptyList(),
                    null,
                    null,
                    null,
                    null,
                    null,
                ),
            ),
            systemIssuer to mapOf(
                // ID プロバイダー
                "00000000-0000-0000-0000-000000000000" to ClientConfig(
                    "00000000-0000-0000-0000-000000000000",
                    "id provider",
                    "secret",
                    ClientType.CONFIDENTIAL,
                    emptyList(),
                    emptyList(),
                    emptyList(),
                    null,
                    null,
                    null,
                    null,
                    null,
                ),
                "00000000-0000-0000-0000-000000000001" to ClientConfig(
                    "00000000-0000-0000-0000-000000000001",
                    "foo client",
                    "secret",
                    ClientType.CONFIDENTIAL,
                    listOf("openid", "user:read", "user:write", "offline_access"),
                    listOf(GrantType.AUTHORIZATION_CODE, GrantType.CLIENT_CREDENTIALS),
                    listOf("http://localhost/cb"),
                    null,
                    null,
                    null,
                    null,
                    null,
                ),
                // リソースサーバー
                "00000000-0000-0000-0000-000000000002" to ClientConfig(
                    "00000000-0000-0000-0000-000000000002",
                    "resource server",
                    "secret",
                    ClientType.CONFIDENTIAL,
                    emptyList(),
                    emptyList(),
                    emptyList(),
                    null,
                    null,
                    null,
                    null,
                    null,
                ),
                // 管理ツールの認証で使用するクライアント
                "00000000-0000-0000-0000-000000000003" to ClientConfig(
                    "00000000-0000-0000-0000-000000000003",
                    "management system",
                    "secret",
                    ClientType.CONFIDENTIAL,
                    listOf("openid"),
                    listOf(GrantType.AUTHORIZATION_CODE),
                    listOf("http://127.0.0.1:5173/login/oauth2/code/login-client"),
                    null,
                    null,
                    null,
                    null,
                    null,
                ),
            ),
        ),
    )

    val userClaimSetStorageMemory = UserClaimSetStorageMemory(
        mapOf(
            serviceIssuer to mapOf(),
            systemIssuer to mapOf(),
        ),
    )

    val clientAuthenticationManager = ClientAuthenticationManager(
        listOf(
            ClientSecretPostAuthenticator(clientConfigStorage),
            ClientSecretBasicAuthenticator(clientConfigStorage),
        ),
    )

    val config = Config(
        issuerConfigStorage = issuerConfigStorage,
        jwkConfigStorage = jwkConfigStorageMemory,
        clientConfigStorage = clientConfigStorage,
        authorizationRequestDataStorage = AuthorizationRequestDataStorageMemory(),
        authorizationCodeStorage = AuthorizationCodeStorageMemory(),
        accessTokenStorage = AccessTokenStorageMemory(),
        refreshTokenStorage = RefreshTokenStorageMemory(),
        userClaimSetStorage = userClaimSetStorageMemory,
        clientAuthenticationManager = clientAuthenticationManager,
    )

    val provider = Provider(config)

    install(OidcProvider) {
        this.provider = provider
        issuerResolver = {
            "${it.request.local.scheme}://${it.request.local.serverHost}:${it.request.local.serverPort}"
        }
        authorizationRequestErrorHandler = {
            when (it) {
                is AuthenticationErrorResponse -> call.respondRedirect(it.toRedirectUrl())
                is AuthorizationErrorResponse -> call.respondRedirect(it.toRedirectUrl())
                is InvalidClient -> call.respondText { "invalid client" }
                is InvalidRedirectUri -> call.respondText { "invalid redirect uri" }
            }
        }
        authorizationRequestSuccessHandler = {
            call.response.cookies.append(
                name = "key",
                value = it.key,
                httpOnly = true,
                path = "/",
            )
            call.respondRedirect("/sign-in")
        }
    }
}
