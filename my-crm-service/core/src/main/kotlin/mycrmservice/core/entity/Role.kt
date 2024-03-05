package mycrmservice.core.entity

import java.util.UUID

/**
 * ロール
 */
class Role(
    /**
     * ID
     */
    val id: UUID,
    /**
     * 名前
     */
    val name: String,
    /**
     * タイプ
     */
    val type: RoleType,
    /**
     * パーミッションセット
     */
    val permissionSet: PermissionSet,
)

/**
 * パーミッションセット
 */
class PermissionSet(
    /**
     * パーミッション
     */
    permissions: Map<Permission, Boolean>,
) {
    private val _permissions = permissions.toMutableMap()

    /**
     * パーミッション
     */
    val permissions
        get() = _permissions.toMap()

    /**
     * 指定したパーミッションが許可されているか
     *
     * @param permission パーミッション
     * @return 許可されている場合は true。されていない場合は false。
     */
    fun permitted(permission: Permission): Boolean {
        return _permissions[permission] ?: false
    }

    /**
     * 指定したパーミッションを許可する
     *
     * @param permission パーミッション
     */
    fun grant(permission: Permission) {
        _permissions[permission] = true
    }

    /**
     * 指定したパーミッションを拒否する
     *
     * @param permission パーミッション
     */
    fun deny(permission: Permission) {
        _permissions[permission] = false
    }
}

/**
 * ロールタイプ
 */
enum class RoleType {
    /**
     * 管理者ロール
     */
    ADMIN,

    /**
     * メンバーロール
     */
    MEMBER,

    /**
     * カスタムロール
     */
    CUSTOM,
}

/**
 * パーミッション
 */
enum class Permission(
    /**
     * 対象リソース
     */
    val resource: String,
    /**
     * 操作
     */
    val action: Action,
) {
    USER_READ("user", Action.Read),
    USER_WRITE("user", Action.Write),
    PAYMENT_READ("payment", Action.Read),
    PAYMENT_WRITE("payment", Action.Write),
    SYSTEM_USER_READ("system_user", Action.Read),
    SYSTEM_USER_WRITE("system_user", Action.Write),
    TENANT_READ("tenant", Action.Read),
    TENANT_WRITE("tenant", Action.Write),
}

/**
 * アクション
 */
sealed class Action(
    /**
     * 値
     */
    val value: String,
) {
    /**
     * Read
     */
    data object Read : Action("read")

    /**
     * Write
     */
    data object Write : Action("write")
}
