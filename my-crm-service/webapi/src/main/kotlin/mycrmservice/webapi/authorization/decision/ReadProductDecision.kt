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
 * プロダクト読み取りの認可判定
 */
@Component
class ReadProductDecision : DecisionFunction<Action.Read, Product> {
    override fun allow(actor: Actor, action: Action.Read, resource: Product): Boolean {
        return when (actor) {
            is Actor.ServiceApplication -> actor.scopes.contains(Scope.PRODUCT_READ)
            is Actor.ServiceUser -> actor.scopes.contains(Scope.PRODUCT_READ)
            is Actor.SystemApplication -> actor.permissions.contains(Permission.PRODUCT_READ)
            is Actor.SystemUser -> actor.role.permissionSet.permitted(Permission.PRODUCT_READ)
        }
    }

    override fun key() = this.generateKey()
}
