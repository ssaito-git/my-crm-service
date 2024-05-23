package mycrmservice.webapi.authorization.decision

import mycrmservice.core.authorization.Action
import mycrmservice.core.entity.Product
import mycrmservice.webapi.authorization.Actor
import mycrmservice.webapi.authorization.DecisionFunction
import mycrmservice.webapi.authorization.generateKey
import org.springframework.stereotype.Component

@Component
class ProductListReadDecision : DecisionFunction<Action.Read, List<Product>> {
    override fun allow(actor: Actor, action: Action.Read, resource: List<Product>): Boolean {
        when (actor) {
            is Actor.ServiceApplication -> TODO()
            is Actor.ServiceUser -> TODO()
            is Actor.SystemApplication -> TODO()
            is Actor.SystemUser -> TODO()
        }
    }

    override fun key() = this.generateKey()
}
