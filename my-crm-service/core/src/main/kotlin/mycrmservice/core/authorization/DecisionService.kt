package mycrmservice.core.authorization

/**
 * 認可の判定サービス
 */
interface DecisionService {
    /**
     * アクター、アクション、リソースをもとに認可を判定する
     *
     * @param actor アクター
     * @param action アクション
     * @param resource リソース
     * @return 許可された場合は true。許可されなかった場合は false。
     */
    fun allow(actor: ActorInterface, action: Action, resource: Any): Boolean
}
