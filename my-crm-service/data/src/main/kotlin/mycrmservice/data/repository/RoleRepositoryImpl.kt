package mycrmservice.data.repository

import mycrmservice.core.entity.Permission
import mycrmservice.core.entity.PermissionSet
import mycrmservice.core.entity.Role
import mycrmservice.core.entity.RoleType
import mycrmservice.core.repository.RoleRepository
import mycrmservice.data.jooq.tables.references.PERMISSIONS
import mycrmservice.data.jooq.tables.references.ROLES
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.util.UUID

/**
 * ロールリポジトリの実装
 */
@Repository
open class RoleRepositoryImpl(private val context: DSLContext) : RoleRepository {
    override fun findById(id: UUID): Role? {
        val role = context.selectFrom(ROLES)
            .where(ROLES.ID.eq(id))
            .fetchOne()
            ?: return null

        val permissions = context.selectFrom(PERMISSIONS)
            .where(PERMISSIONS.ROLE_ID.eq(id))
            .mapNotNull { record ->
                Permission.entries.firstOrNull {
                    it.resource == record.resource && it.action.value == record.action
                }?.let {
                    it to record.isGranted
                }
            }
            .toMap()

        return Role(
            role.id,
            role.name,
            RoleType.ADMIN,
            PermissionSet(permissions),
        )
    }
}
