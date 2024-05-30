package mycrmservice.webapi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock

/**
 * Clock のコンフィグ
 */
@Configuration
class ClockConfig {
    /**
     * Clock
     */
    @Bean
    fun clock(): Clock {
        return Clock.systemDefaultZone()
    }
}
