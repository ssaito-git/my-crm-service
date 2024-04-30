package mycrmservice.webapi.authorization

import kotlin.reflect.KClass

/**
 * Decision function key
 *
 * @param A Actor Type
 * @param B Action Type
 * @param C Resource Type
 */
data class DecisionFunctionKey<A : Any, B : Any, C : Any>(
    private val actor: KClass<out A>,
    private val action: KClass<out B>,
    private val resource: KClass<out C>,
)
