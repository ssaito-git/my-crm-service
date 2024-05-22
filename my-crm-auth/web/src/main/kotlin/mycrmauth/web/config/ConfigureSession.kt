package mycrmauth.web.config

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.sessions.SessionStorageMemory
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import mycrmauth.web.session.SignInSession

/**
 * セッションの設定
 */
fun Application.configureSession() {
    install(Sessions) {
        cookie<SignInSession>("sign_in_session", SessionStorageMemory())
    }
}
