package mycrmauth.web

import io.ktor.server.engine.addShutdownHook
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import mycrmauth.web.config.configureAuthentication
import mycrmauth.web.config.configureFreeMarker
import mycrmauth.web.config.configureOidcProvider
import mycrmauth.web.config.configureOidcProviderRouting
import mycrmauth.web.config.configureRouting
import mycrmauth.web.config.configureSerialization
import mycrmauth.web.config.configureSession

/**
 * メイン関数
 */
fun main(args: Array<String>) {
    val env = commandLineEnvironment(args) {
        module {
            configureFreeMarker()
            configureSerialization()
            configureSession()
            configureAuthentication()
            configureOidcProvider()
            configureOidcProviderRouting()
            configureRouting()
        }
        watchPaths = listOf("classes")
        developmentMode = true
    }

    embeddedServer(Netty, environment = env).apply {
        addShutdownHook {
            println("shutdown.")
        }
    }.start(wait = true)
}
