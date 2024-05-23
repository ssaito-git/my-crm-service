package mycrmservice.webapi.config

import jakarta.servlet.http.HttpServletRequest
import mycrmservice.core.authorization.Action
import mycrmservice.core.entity.Permission
import mycrmservice.webapi.authorization.Actor
import mycrmservice.webapi.authorization.DecisionFunction
import mycrmservice.webapi.authorization.Scope
import mycrmservice.webapi.authorization.generateKey
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
import org.springframework.stereotype.Component
import java.util.UUID

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

data class User(
    val id: UUID,
    val name: String,
    val email: String,
)

/**
 * ユーザー読み取りの認可判定
 */
@Component
class UserReadDecisionFunction : DecisionFunction<Action.Read, User> {
    override fun allow(actor: Actor, action: Action.Read, resource: User): Boolean {
        return when (actor) {
            is Actor.ServiceApplication -> actor.scopes.contains(Scope.USER_READ)
            is Actor.ServiceUser -> actor.scopes.contains(Scope.USER_READ) && actor.id == resource.id
            is Actor.SystemApplication -> TODO()
            is Actor.SystemUser -> actor.role.permissionSet.permitted(Permission.USER_READ)
        }
    }

    override fun key() = this.generateKey()
}
