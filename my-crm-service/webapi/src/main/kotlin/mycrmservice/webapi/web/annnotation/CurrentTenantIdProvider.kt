package mycrmservice.webapi.web.annnotation

import mycrmservice.webapi.oauth2.CustomBearerTokenAuthentication
import org.springframework.stereotype.Component
import java.util.UUID

/**
 * テナント ID プロバイダー
 */
@Component("currentTenantIdProvider")
class CurrentTenantIdProvider {
    /**
     * テナント ID を取得する。
     *
     * @param authentication アクセストークンの情報
     * @return テナント ID
     */
    fun get(authentication: CustomBearerTokenAuthentication): UUID {
        return authentication.tenantId
    }
}
