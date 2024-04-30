package mycrmservice.webapi.controller.internal.systemuser

import jakarta.validation.constraints.NotBlank
import mycrmservice.core.authorization.Action
import mycrmservice.core.authorization.DecisionService
import mycrmservice.core.entity.Permission
import mycrmservice.core.entity.SystemUser
import mycrmservice.core.repository.SystemUserRepository
import mycrmservice.webapi.authorization.Actor
import mycrmservice.webapi.authorization.DecisionFunction
import mycrmservice.webapi.authorization.generate
import mycrmservice.webapi.web.annnotation.CurrentActor
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * システムユーザー認証のコントローラー
 */
@RestController
class SystemUserAuthenticationController(
    private val systemUserRepository: SystemUserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val decisionService: DecisionService,
) {
    /**
     * 認証
     *
     * @param actor アクター
     * @param body リクエスト
     * @return レスポンス
     */
    @Transactional
    @PostMapping("/system-users/authentication")
    fun authenticate(
        @CurrentActor actor: Actor,
        @RequestBody @Validated body: Request,
    ): ResponseEntity<Response> {
        val request = SystemUserPasswordAuthentication.Credential(
            body.username,
            body.password,
        )
        val useCase = SystemUserPasswordAuthentication(
            systemUserRepository,
            passwordEncoder,
            decisionService,
        )
        val authenticatedSystemUser = useCase.authenticate(actor, request)

        return if (authenticatedSystemUser != null) {
            ResponseEntity.ok(Response(authenticatedSystemUser.id.toString()))
        } else {
            ResponseEntity.badRequest().build()
        }
    }

    /**
     * リクエスト
     */
    data class Request(
        /**
         * ユーザー名
         */
        @field:NotBlank
        val username: String,

        /**
         * パスワード
         */
        @field:NotBlank
        val password: String,
    )

    /**
     * レスポンス
     */
    data class Response(
        /**
         * システムユーザー ID
         */
        val id: String,
    )
}

/**
 * システムユーザーパスワード認証インタラクター
 */
class SystemUserPasswordAuthentication(
    private val systemUserRepository: SystemUserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val decisionService: DecisionService,
) {
    /**
     * 認証
     *
     * @param actor アクター
     * @param credential クレデンシャル
     * @return 認証に成功した [SystemUser]。失敗した場合は null。
     */
    fun authenticate(actor: Actor, credential: Credential): SystemUser? {
        val systemUser = systemUserRepository.findByEmail(credential.username)
            ?: return null

        if (!decisionService.allow(actor, Action.Read, systemUser)) {
            return null
        }

        return if (passwordEncoder.matches(credential.password, systemUser.password)) {
            systemUser
        } else {
            null
        }
    }

    /**
     * クレデンシャル
     */
    data class Credential(
        /**
         * ユーザー名
         */
        val username: String,
        /**
         * パスワード
         */
        val password: String,
    )
}

/**
 * システムユーザー読み取りの認可判定
 */
@Component
class SystemUserReadDecisionFunction : DecisionFunction<Actor, Action.Read, SystemUser> {
    override fun allow(actor: Actor, action: Action.Read, resource: SystemUser): Boolean {
        return when (actor) {
            is Actor.ServiceApplication, is Actor.ServiceUser -> false
            is Actor.SystemApplication -> true
            is Actor.SystemUser -> actor.id == resource.id || actor.role.permissionSet.permitted(Permission.USER_READ)
        }
    }

    override fun key() = this.generate()
}
