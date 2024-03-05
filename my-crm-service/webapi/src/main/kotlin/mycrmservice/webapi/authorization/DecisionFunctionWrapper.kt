package mycrmservice.webapi.authorization

/**
 * Decision function wrapper
 */
class DecisionFunctionWrapper<A : Any, B : Any, C : Any>(
    /**
     * Decision function
     */
    val function: (actor: A, action: B, resource: C) -> Boolean,
) : DecisionFunction<A, B, C> {
    override fun allow(actor: A, action: B, resource: C): Boolean {
        return function(actor, action, resource)
    }

    override fun key(): DecisionFunctionKey<A, B, C> {
        TODO("Not yet implemented")
    }
}
