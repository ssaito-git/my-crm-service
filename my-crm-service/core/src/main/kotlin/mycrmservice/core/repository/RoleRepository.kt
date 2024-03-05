package mycrmservice.core.repository

import mycrmservice.core.entity.Role
import java.util.*

/**
 * ロールのリポジトリ
 */
interface RoleRepository {
    /**
     * ID で検索する。
     *
     * @param id ID
     * @return ID に一致する [Role]。存在しない場合は null。
     */
    fun findById(id: UUID): Role?
}
