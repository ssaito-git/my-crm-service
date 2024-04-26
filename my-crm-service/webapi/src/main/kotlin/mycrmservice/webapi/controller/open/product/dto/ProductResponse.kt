package mycrmservice.webapi.controller.open.product.dto

import io.swagger.v3.oas.annotations.media.Schema
import mycrmservice.core.entity.Product

/**
 * プロダクトレスポンス
 */
@Schema(name = "Product")
data class ProductResponse(
    /**
     * ID
     */
    @Schema(required = true, description = "プロダクトごとに一意な ID")
    val id: String,
    /**
     * SKU
     */
    @Schema(required = true, description = "プロダクトごとに一意な SKU")
    val sku: String,
    /**
     * 名前
     */
    @Schema(required = true, description = "名前")
    val name: String,
    /**
     * 詳細
     */
    @Schema(required = true, description = "詳細")
    val description: String,
    /**
     * プロダクトが有効かどうか
     */
    @Schema(required = true, description = "プロダクトが有効かどうか")
    val active: Boolean,
) {
    companion object {
        /**
         * プロダクトからレスポンスを作成します。
         *
         * @param product プロダクト
         * @return レスポンス
         */
        fun from(product: Product): ProductResponse {
            return ProductResponse(
                product.id.toString(),
                product.sku,
                product.name,
                product.description,
                product.active,
            )
        }
    }
}
