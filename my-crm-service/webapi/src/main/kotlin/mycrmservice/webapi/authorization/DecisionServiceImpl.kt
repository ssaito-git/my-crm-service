package mycrmservice.webapi.authorization

import mycrmservice.core.authorization.Action
import mycrmservice.core.authorization.ActorInterface
import mycrmservice.core.authorization.DecisionService

/**
 * Decision service
 */
class DecisionServiceImpl : DecisionService {
    private val decisionFunctions =
        mutableMapOf<DecisionFunctionKey<out Any, out Any, out Any>, DecisionFunction<Any, Any, Any>>()

    /**
     * Decision function を追加する
     *
     * @param decisionFunction Decision function
     */
    fun <A : Any, B : Any, C : Any> addDecisionFunction(
        decisionFunction: DecisionFunction<out A, out B, out C>,
    ) {
        @Suppress("UNCHECKED_CAST")
        decisionFunctions[decisionFunction.key()] =
            decisionFunction as? DecisionFunction<in Any, in Any, in Any> ?: return
    }

    override fun allow(actor: ActorInterface, action: Action, resource: Any): Boolean {
        val key = DecisionFunctionKey(actor::class, action::class, resource::class)

        return decisionFunctions[key]?.allow(
            actor,
            action,
            resource,
        ) ?: false
    }
}
