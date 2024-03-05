package mycrmservice.webapi.aop

import mycrmservice.webapi.oauth2.CustomBearerTokenAuthentication
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.jdbc.datasource.DataSourceUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import javax.sql.DataSource

/**
 * テナント ID を RLS のパラメーターに設定するアスペクト
 */
@Aspect
@Component
class RowLevelSecurityParameterSetAspect(private val dataSource: DataSource) {
    /**
     * メソッドに設定されたトランザクションの前に実行される
     */
    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    fun beforeTransactionalMethodExecuted() {
        val tenantId = (SecurityContextHolder.getContext().authentication as? CustomBearerTokenAuthentication)?.tenantId
            ?: error("テナント ID を取得できませんdした。")

        DataSourceUtils.getConnection(dataSource).createStatement().use {
            it.execute("SET LOCAL app.current_tenant = '$tenantId'")
        }
    }
}
