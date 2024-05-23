package mycrmservice.webapi.authorization

import mycrmservice.core.authorization.Action
import kotlin.reflect.typeOf

/**
 * 認可判定ファンクション
 */
interface DecisionFunction<ActionT : Action, ResourceT : Any> {
    /**
     * アクターがリソースにアクションを実行できるか判定する
     *
     * @param actor アクター
     * @param action アクション
     * @param resource リソース
     * @return 許可された場合は true。許可されない場合は false。
     */
    fun allow(actor: Actor, action: ActionT, resource: ResourceT): Boolean

    /**
     * キーの取得
     *
     * @return キー
     */
    fun key(): DecisionFunctionKey
}

/**
 * キーを生成する。
 *
 * @return キー
 */
inline fun <reified ActionT : Action, reified ResourceT : Any>
    DecisionFunction<ActionT, ResourceT>.generateKey(): DecisionFunctionKey {
    val actionType = typeOf<ActionT>()
    val resourceType = typeOf<ResourceT>()
    return DecisionFunctionKey(actionType, resourceType)
}
