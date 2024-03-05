package mycrmservice.core.repository

import mycrmservice.core.entity.SystemUser
import java.util.*

/**
 * システムユーザーのリポジトリ
 */
interface SystemUserRepository {
    /**
     * ID で検索する。
     *
     * @param id ID
     * @return ID に一致する [SystemUser]。存在しない場合は null。
     */
    fun findById(id: UUID): SystemUser?

    /**
     * メールアドレスで検索する。
     *
     * @param email メールアドレス
     * @return メールアドレスに一致する [SystemUser]。存在しない場合は null。
     */
    fun findByEmail(email: String): SystemUser?
}