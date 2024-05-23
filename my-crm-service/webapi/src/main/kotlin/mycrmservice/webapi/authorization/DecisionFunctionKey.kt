package mycrmservice.webapi.authorization

import kotlin.reflect.KType

/**
 * 認可判定ファンクションキー
 */
data class DecisionFunctionKey(
    private val action: KType,
    private val resource: KType,
)
