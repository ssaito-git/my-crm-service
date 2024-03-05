package mycrmadmin.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * App
 */
@SpringBootApplication
class AdminApp

/**
 * Main
 */
fun main(vararg args: String) {
    runApplication<AdminApp>(*args)
}
