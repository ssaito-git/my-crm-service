package mycrmservice.data.repository

import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.junit.jupiter.api.Test
import java.sql.DriverManager
import java.util.*

class RepositoryTest {
    @Test
    fun test() {
        DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/crm_service",
            "crm_service_user",
            "secret",
        ).use { connection ->
            val dslContext = DSL.using(connection, SQLDialect.POSTGRES)
            dslContext.execute("SET app.current_tenant = '00000001-0000-0000-0000-000000000001'")
            val repository = SystemUserRepositoryImpl(dslContext)
            val systemUser = repository.findById(UUID.fromString("00000002-0000-0000-0000-000000000001"))
            println(systemUser)
        }
    }

    @Test
    fun test2() {
        DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/crm_service",
            "crm_service_user",
            "secret",
        ).use { connection ->
            val dslContext = DSL.using(connection, SQLDialect.POSTGRES)
            dslContext.execute("SET app.current_tenant = '00000001-0000-0000-0000-000000000001'")
            val repository = RoleRepositoryImpl(dslContext)
            val role = repository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002"))
            println(role)
        }
    }
}
