package mycrmservice.webapi.config

import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManagerResolver
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.DelegatingPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

/**
 * セキュリティーコンフィグ
 */
@Configuration
@EnableMethodSecurity
class SecurityConfig {
    /**
     * セキュリティフィルター
     */
    @Bean
    fun filterChain(
        http: HttpSecurity,
        customAuthenticationManagerResolver: AuthenticationManagerResolver<HttpServletRequest>,
    ): SecurityFilterChain {
        http {
            csrf {
                disable()
            }
            authorizeRequests {
                authorize("/v3/api-docs/**", permitAll)
                authorize(anyRequest, authenticated)
            }
            oauth2ResourceServer {
                authenticationManagerResolver = customAuthenticationManagerResolver
            }
        }

        return http.build()
    }

    /**
     * パスワードエンコーダー
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        val idForEncode = "bcrypt"
        val encoders = mapOf(
            idForEncode to BCryptPasswordEncoder(),
        )
        return DelegatingPasswordEncoder(idForEncode, encoders)
    }
}
