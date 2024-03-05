package mycrmservice.webapi.authorization

import kotlin.reflect.KClass

/**
 * Decision service
 */
class DecisionService {
    private val decisionFunctions =
        mutableMapOf<DecisionFunctionKey<out Any, out Any, out Any>, DecisionFunction<in Any, in Any, in Any>>()

    /**
     * Decision function を追加する
     *
     * @param actor アクターのタイプ
     * @param action アクションのタイプ
     * @param resource リソースのタイプ
     * @param decisionFunction Decision function
     */
    fun <A : Any, B : Any, C : Any> addDecisionFunction(
        actor: KClass<out A>,
        action: KClass<out B>,
        resource: KClass<out C>,
        decisionFunction: DecisionFunction<out A, out B, out C>,
    ) {
        val key = DecisionFunctionKey(actor, action, resource)
        @Suppress("UNCHECKED_CAST")
        decisionFunctions[key] = decisionFunction as? DecisionFunction<in Any, in Any, in Any> ?: return
    }

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

    /**
     * Decision function を追加する
     *
     * @param decisionFunction Decision function
     */
    inline fun <reified A : Any, reified B : Any, reified C : Any> addDecisionFunction(
        noinline decisionFunction: (A, B, C) -> Boolean,
    ) {
        addDecisionFunction(A::class, B::class, C::class, DecisionFunctionWrapper(decisionFunction))
    }

    /**
     * アクターがリソースにアクションを実行できるか判定する
     *
     * @param key キー
     * @param actor アクター
     * @param action アクション
     * @param resource リソース
     * @return 許可された場合は true。許可されない場合は false。
     */
    fun <A : Any, B : Any, C : Any> allow(
        key: DecisionFunctionKey<A, B, C>,
        actor: A,
        action: B,
        resource: C,
    ): Boolean {
        return decisionFunctions[key]?.allow(
            actor,
            action,
            resource,
        ) ?: false
    }

    /**
     * アクターがリソースにアクションを実行できるか判定する
     *
     * @param actor アクター
     * @param action アクション
     * @param resource リソース
     * @return 許可された場合は true。許可されない場合は false。
     */
    inline fun <reified A : Any, reified B : Any, reified C : Any> allow(
        actor: A,
        action: B,
        resource: C,
    ): Boolean {
        val key = DecisionFunctionKey(A::class, B::class, C::class)
        return allow(key, actor, action, resource)
    }
}
