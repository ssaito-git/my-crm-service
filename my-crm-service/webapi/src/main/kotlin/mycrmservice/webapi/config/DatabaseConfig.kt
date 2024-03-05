package mycrmservice.webapi.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

/**
 * Database config
 */
@Configuration
@EnableTransactionManagement
class DatabaseConfig {
    /**
     * Data source
     */
    @Bean
    fun dataSource(): DataSource {
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:postgresql://localhost:5432/crm_service"
            username = "crm_service_user"
            password = "secret"
        }

        return HikariDataSource(config)
    }
}
