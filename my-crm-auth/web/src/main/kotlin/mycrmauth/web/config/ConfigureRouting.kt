package mycrmauth.web.config

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.application
import io.ktor.server.application.call
import io.ktor.server.application.plugin
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.freemarker.FreeMarkerContent
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set
import kotlinx.serialization.Serializable
import mycrmauth.web.session.SignInSession
import mycrmauth.web.viewmodel.SignInFormViewModel
import myoidcprovider.core.authorization.AccessTokenGenerator
import myoidcprovider.core.metadata.ResponseMode
import myoidcprovider.core.request.authorization.AuthorizationResponseError
import myoidcprovider.core.util.Clock
import myoidcprovider.ktor.plugin.OidcProvider
import myoidcprovider.ktor.util.toFormPostHtml
import myoidcprovider.ktor.util.toRedirectUrl
import java.util.UUID

/**
 * ルーティングの設定
 */
fun Application.configureRouting() {
    routing {
        get("/sign-in") {
            if (call.principal<SignInSession>() != null) {
                // サインイン済みの場合は同意画面にリダイレクトする
                call.respondRedirect("/consent")
            } else {
                call.respond(FreeMarkerContent("SignInForm.ftl", mapOf("viewModel" to SignInFormViewModel())))
            }
        }
        post("/sign-in") {
            val parameters = call.receiveParameters()
            val username = parameters["username"]
            val password = parameters["password"]

            if (username.isNullOrBlank()) {
                val viewModel = SignInFormViewModel(usernameInputError = "Username is required.")
                call.respond(FreeMarkerContent("SignInForm.ftl", mapOf("viewModel" to viewModel)))
                return@post
            }

            if (password.isNullOrBlank()) {
                val viewModel = SignInFormViewModel(passwordInputError = "Password is required.")
                call.respond(FreeMarkerContent("SignInForm.ftl", mapOf("viewModel" to viewModel)))
                return@post
            }

            val result = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json()
                }
            }.use { client ->
                val oidcProvider = application.plugin(OidcProvider)
                val issuerConfig = oidcProvider.provider.config.issuerConfigStorage.findByIssuer(
                    oidcProvider.issuerResolver(call),
                ) ?: error("")
                val clientConfig = oidcProvider.provider.config.clientConfigStorage.findById(
                    issuerConfig.issuer,
                    "00000000-0000-0000-0000-000000000000",
                ) ?: error("")
                val accessToken = AccessTokenGenerator(
                    Clock,
                ).generate(issuerConfig, clientConfig, null, null)
                oidcProvider.provider.config.accessTokenStorage.save(accessToken)

                @Serializable
                data class SystemUserAuthenticationRequest(val username: String, val password: String)

                @Serializable
                data class SystemUserAuthenticationResponse(val id: String)

                val response = client.post("http://127.0.0.1:8090/system-users/authentication") {
                    contentType(ContentType.Application.Json)
                    setBody(SystemUserAuthenticationRequest(username, password))
                    headers {
                        append("Authorization", "Bearer ${accessToken.token}")
                    }
                }

                if (response.status == HttpStatusCode.OK) {
                    val body: SystemUserAuthenticationResponse = response.body()
                    body.id
                } else {
                    null
                }
            }

            if (result != null) {
                val id = UUID.fromString(result)
                call.sessions.set(SignInSession(id))

//                call.respondRedirect("/consent")

                val key = call.request.cookies["key"]

                if (key == null) {
                    call.respondText("key does not exists.")
                    return@post
                }

                val oidcProvider = application.plugin(OidcProvider)
                val issuer = oidcProvider.issuerResolver(call)

                oidcProvider.provider.handleAuthorizationRequestPostProcessHandler(
                    issuer,
                    id.toString(),
                    key,
                    true,
                ).onSuccess {
                    when (it.responseMode) {
                        ResponseMode.FORM_POST -> {
                            call.respondText(ContentType.Text.Html) { it.toFormPostHtml() }
                        }

                        ResponseMode.QUERY, ResponseMode.FRAGMENT, null -> {
                            call.respondRedirect(it.toRedirectUrl())
                        }
                    }
                }.onFailure {
                    when (it) {
                        is AuthorizationResponseError.ErrorResponse -> {
                            call.respondRedirect(it.toRedirectUrl())
                        }
                        AuthorizationResponseError.InvalidRequest -> {
                            call.respondText("invalid request.")
                        }
                    }
                }
            } else {
                val viewModel = SignInFormViewModel(signInError = "Username or password is incorrect.")
                call.respond(FreeMarkerContent("SignInForm.ftl", mapOf("viewModel" to viewModel)))
            }
        }
    }

    routing {
        authenticate("sign_in_session") {
            post("/consent") {
                val key = call.request.cookies["key"]

                if (key == null) {
                    call.respondText("key does not exists.")
                    return@post
                }

                val signInSession = call.principal<SignInSession>()

                if (signInSession == null) {
                    call.respondText("")
                    return@post
                }

                val oidcProvider = application.plugin(OidcProvider)
                val issuer = oidcProvider.issuerResolver(call)

                oidcProvider.provider.handleAuthorizationRequestPostProcessHandler(
                    issuer,
                    signInSession.id.toString(),
                    key,
                    true,
                ).onSuccess {
                    when (it.responseMode) {
                        ResponseMode.FORM_POST -> {
                            call.respondText(ContentType.Text.Html) { it.toFormPostHtml() }
                        }

                        ResponseMode.QUERY, ResponseMode.FRAGMENT, null -> {
                            call.respondRedirect(it.toRedirectUrl())
                        }
                    }
                }.onFailure {
                    when (it) {
                        is AuthorizationResponseError.ErrorResponse -> {
                            call.respondRedirect(it.toRedirectUrl())
                        }
                        AuthorizationResponseError.InvalidRequest -> {
                            call.respondText("invalid request.")
                        }
                    }
                }
            }
        }
    }
}
