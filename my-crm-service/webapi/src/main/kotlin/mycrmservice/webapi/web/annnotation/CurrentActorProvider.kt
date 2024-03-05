package mycrmservice.webapi.web.annnotation

import mycrmservice.core.repository.RoleRepository
import mycrmservice.core.repository.SystemUserRepository
import mycrmservice.webapi.authorization.Actor
import mycrmservice.webapi.authorization.ActorType
import mycrmservice.webapi.authorization.Scope
import mycrmservice.webapi.oauth2.CustomBearerTokenAuthentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * カレントアクタープロバイダー
 *
 * アクセストークンから取得した情報をもとに現在のアクターを取得します。
 */
@Component("currentActorProvider")
class CurrentActorProvider(
    /**
     * システムユーザーリポジトリ
     */
    private val systemUserRepository: SystemUserRepository,
    /**
     * ロールリポジトリ
     */
    private val roleRepository: RoleRepository,
) {
    /**
     * 現在のアクターを取得します。
     *
     * @param authentication アクセストークンの情報
     * @return 現在のアクター。
     */
    @Transactional
    fun get(authentication: CustomBearerTokenAuthentication): Actor {
        val subject = authentication.tokenAttributes[OAuth2TokenIntrospectionClaimNames.SUB] as? String
        val clientId = authentication.tokenAttributes[OAuth2TokenIntrospectionClaimNames.CLIENT_ID] as? String
        return when (authentication.actorType) {
            ActorType.SERVICE_USER -> {
                Actor.ServiceUser(UUID.fromString(subject), getScopes(authentication.authorities))
            }

            ActorType.SERVICE_APPLICATION -> {
                Actor.ServiceApplication(UUID.fromString(clientId), getScopes(authentication.authorities))
            }

            ActorType.SYSTEM_USER -> {
                val id = UUID.fromString(subject)
                val systemUser = systemUserRepository.findById(id) ?: error("システムユーザーが存在しません。[$id]")
                val role = roleRepository.findById(systemUser.roleId) ?: error("ロールが存在しません。[${systemUser.roleId}]")
                Actor.SystemUser(systemUser.id, role)
            }

            ActorType.SYSTEM_APPLICATION -> {
                Actor.SystemApplication(UUID.fromString(clientId), emptyList())
            }
        }
    }

    private fun getScopes(authorities: Collection<GrantedAuthority>): List<Scope> {
        return authorities.filter {
            it.authority.startsWith("SCOPE_")
        }.mapNotNull {
            runCatching {
                Scope.valueOf(it.authority.removePrefix("SCOPE_").replace(":", "_").uppercase())
            }.getOrNull()
        }
    }
}
