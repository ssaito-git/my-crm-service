package mycrmauth.web.config

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.session
import io.ktor.server.response.respondText
import mycrmauth.web.session.SignInSession

/**
 * 認証の設定
 */
fun Application.configureAuthentication() {
    install(Authentication) {
        session<SignInSession>("sign_in_session") {
            validate {
                it
            }
            challenge {
                call.respondText("sign-in session error.")
            }
        }
    }
}