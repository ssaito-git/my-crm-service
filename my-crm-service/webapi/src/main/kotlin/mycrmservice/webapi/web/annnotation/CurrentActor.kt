package mycrmservice.webapi.web.annnotation

import org.springframework.security.core.annotation.CurrentSecurityContext

/**
 * カレントアクターを取得する
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@CurrentSecurityContext(expression = "@currentActorProvider.get(authentication)")
annotation class CurrentActor
