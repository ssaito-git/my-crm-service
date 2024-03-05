package mycrmservice.webapi.authorization

/**
 * キーを生成する。
 *
 * @return キー
 */
inline fun <reified A : Any, reified B : Any, reified C : Any>
    DecisionFunction<A, B, C>.generate(): DecisionFunctionKey<A, B, C> {
    return DecisionFunctionKey(A::class, B::class, C::class)
}
