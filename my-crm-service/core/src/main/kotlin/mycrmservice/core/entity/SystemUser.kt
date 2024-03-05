package mycrmservice.core.entity

import java.util.*

/**
 * システムユーザー
 */
data class SystemUser(
    /**
     * ID
     */
    val id: UUID,
    /**
     * 名前
     */
    val name: String,
    /**
     * メールアドレス
     */
    val email: String,
    /**
     * ロール ID
     */
    val roleId: UUID,
    /**
     * パスワード
     */
    val password: String,
)
