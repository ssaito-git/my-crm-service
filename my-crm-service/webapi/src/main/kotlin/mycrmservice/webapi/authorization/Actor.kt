package mycrmservice.webapi.authorization

import mycrmservice.core.entity.Permission
import mycrmservice.core.entity.Role
import java.util.UUID

/**
 * アクター
 */
sealed interface Actor {
    /**
     * サービスユーザー
     */
    data class ServiceUser(
        /**
         * ユーザー ID
         */
        val id: UUID,
        /**
         * スコープ
         */
        val scopes: List<Scope>,
    ) : Actor

    /**
     * サービスアプリケーション
     */
    data class ServiceApplication(
        /**
         * クライアント ID
         */
        val clientId: UUID,
        /**
         * スコープ
         */
        val scopes: List<Scope>,
    ) : Actor

    /**
     * システムユーザー
     */
    data class SystemUser(
        /**
         * システムユーザー ID
         */
        val id: UUID,
        /**
         * ロール
         */
        val role: Role,
    ) : Actor

    /**
     * システムアプリケーション
     */
    data class SystemApplication(
        /**
         * クライアント ID
         */
        val clientId: UUID,
        /**
         * パーミッション
         */
        val permissions: List<Permission>,
    ) : Actor
}
