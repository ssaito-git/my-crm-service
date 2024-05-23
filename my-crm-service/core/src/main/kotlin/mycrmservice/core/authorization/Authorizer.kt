package mycrmservice.core.authorization

import kotlin.reflect.KType
import kotlin.reflect.typeOf

/**
 * 認可の判定を行う
 */
interface Authorizer {
    /**
     * アクター、アクション、リソースをもとに認可を判定する
     *
     * @param actor アクター
     * @param action アクション
     * @param actionType アクションのタイプ
     * @param resource リソース
     * @param resourceType リソースのタイプ
     * @return 許可された場合は true。許可されなかった場合は false。
     */
    fun allow(actor: ActorInterface, action: Action, actionType: KType, resource: Any, resourceType: KType): Boolean
}

/**
 * アクター、アクション、リソースをもとに認可を判定する
 *
 * @param actor アクター
 * @param action アクション
 * @param resource リソース
 * @return 許可された場合は true。許可されなかった場合は false。
 */
inline fun <reified ActionT : Action, reified ResourceT : Any> Authorizer.allow(
    actor: ActorInterface,
    action: ActionT,
    resource: ResourceT,
): Boolean {
    val actionType = typeOf<ActionT>()
    val resourceType = typeOf<ResourceT>()

    return this.allow(actor, action, actionType, resource, resourceType)
}
