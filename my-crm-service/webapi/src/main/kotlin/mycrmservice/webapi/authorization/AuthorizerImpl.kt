package mycrmservice.webapi.authorization

import mycrmservice.core.authorization.Action
import mycrmservice.core.authorization.ActorInterface
import mycrmservice.core.authorization.Authorizer
import kotlin.reflect.KType

/**
 * 認可の判定実装
 */
class AuthorizerImpl : Authorizer {
    private val decisionFunctionMap = mutableMapOf<DecisionFunctionKey, DecisionFunction<in Action, in Any>>()

    override fun allow(
        actor: ActorInterface,
        action: Action,
        actionType: KType,
        resource: Any,
        resourceType: KType,
    ): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * 認可判定ファンクションを登録する。
     *
     * @param decisionFunction 認可判定ファンクション
     */
    fun <ActionT : Action, ResourceT : Any> add(decisionFunction: DecisionFunction<ActionT, ResourceT>) {
        @Suppress("UNCHECKED_CAST")
        decisionFunctionMap[decisionFunction.key()] = decisionFunction as? DecisionFunction<Action, Any>
            ?: error("認可判定ファンクションの登録に失敗しました")
    }
}
