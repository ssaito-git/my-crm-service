package mycrmservice.webapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * App
 */
@SpringBootApplication(scanBasePackages = ["mycrmservice.webapi", "mycrmservice.data"])
class ServiceApp

/**
 * Main
 */
fun main(vararg args: String) {
    System.setProperty("org.jooq.no-logo", "true")
    System.setProperty("org.jooq.no-tips", "true")
    runApplication<ServiceApp>(*args)
}
