package mycrmservice.webapi.authorization

/**
 * Decision function
 */
interface DecisionFunction<A : Any, B : Any, C : Any> {
    /**
     * アクターがリソースにアクションを実行できるか判定する
     *
     * @param actor アクター
     * @param action アクション
     * @param resource リソース
     * @return 許可された場合は true。許可されない場合は false。
     */
    fun allow(actor: A, action: B, resource: C): Boolean

    /**
     * キーの取得
     *
     * @return キー
     */
    fun key(): DecisionFunctionKey<A, B, C>
}
