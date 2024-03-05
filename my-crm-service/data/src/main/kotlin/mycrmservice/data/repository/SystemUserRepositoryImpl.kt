package mycrmservice.data.repository

import mycrmservice.core.entity.SystemUser
import mycrmservice.core.repository.SystemUserRepository
import mycrmservice.data.jooq.tables.references.SYSTEM_USERS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.util.*

/**
 * システムユーザーリポジトリの実装
 */
@Repository
open class SystemUserRepositoryImpl(private val context: DSLContext) : SystemUserRepository {
    override fun findById(id: UUID): SystemUser? {
        return context.selectFrom(SYSTEM_USERS)
            .where(SYSTEM_USERS.ID.eq(id))
            .fetchOne()
            ?.let {
                SystemUser(
                    it.id,
                    it.name,
                    it.email,
                    it.roleId,
                    it.password,
                )
            }
    }

    override fun findByEmail(email: String): SystemUser? {
        return context.selectFrom(SYSTEM_USERS)
            .where(SYSTEM_USERS.EMAIL.eq(email))
            .fetchOne()
            ?.let {
                SystemUser(
                    it.id,
                    it.name,
                    it.email,
                    it.roleId,
                    it.password,
                )
            }
    }
}
