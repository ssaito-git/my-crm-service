package mycrmservice.webapi.web.annnotation

import org.springframework.security.core.annotation.CurrentSecurityContext

/**
 * 現在のテナント ID を取得する
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@CurrentSecurityContext(expression = "@currentTenantIdProvider.get(authentication)")
annotation class CurrentTenantId
