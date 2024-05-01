package mycrmservice.webapi.authorization

import mycrmservice.core.authorization.Action

/**
 * スコープ
 */
enum class Scope(
    /**
     * 対象リソース
     */
    val resource: String,
    /**
     * 操作
     */
    val action: Action,
) {
    USER_READ("user", Action.Read),
    USER_WRITE("user", Action.Write),
    PAYMENT_READ("payment", Action.Read),
    PAYMENT_WRITE("payment", Action.Write),
    PRODUCT_READ("product", Action.Read),
    PRODUCT_WRITE("product", Action.Write),
}
