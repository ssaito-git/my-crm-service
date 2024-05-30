package mycrmservice.webapi.authorization.decision

import mycrmservice.core.authorization.Action
import mycrmservice.core.entity.Permission
import mycrmservice.core.entity.Product
import mycrmservice.webapi.authorization.Actor
import mycrmservice.webapi.authorization.DecisionFunction
import mycrmservice.webapi.authorization.Scope
import mycrmservice.webapi.authorization.generateKey
import org.springframework.stereotype.Component

/**
 * プロダクト書き込みの認可判定
 */
@Component
class WriteProductDecision : DecisionFunction<Action.Write, Product> {
    override fun allow(actor: Actor, action: Action.Write, resource: Product): Boolean {
        return when (actor) {
            is Actor.ServiceApplication -> actor.scopes.contains(Scope.PRODUCT_WRITE)
            is Actor.ServiceUser -> false
            is Actor.SystemApplication -> actor.permissions.contains(Permission.PRODUCT_WRITE)
            is Actor.SystemUser -> actor.role.permissionSet.permitted(Permission.PRODUCT_WRITE)
        }
    }

    override fun key() = this.generateKey()
}
