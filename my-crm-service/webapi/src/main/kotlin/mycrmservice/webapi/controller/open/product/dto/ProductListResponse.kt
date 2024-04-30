package mycrmservice.webapi.controller.open.product.dto

import io.swagger.v3.oas.annotations.media.Schema
import mycrmservice.core.entity.Product

/**
 * プロダクトリストレスポンス
 */
@Schema(name = "ProductList")
data class ProductListResponse(
    /**
     * プロダクトのリスト
     */
    @Schema(required = true, description = "プロダクトのリスト")
    val products: List<ProductResponse>,
    /**
     * さらにプロダクトが存在するか
     */
    @Schema(required = true, description = "さらにプロダクトが存在するか")
    val hasMore: Boolean,
) {
    companion object {
        /**
         * プロダクトのリストからレスポンスを作成する
         *
         * @param products プロダクトのリスト
         * @param hasMore さらにプロダクトが存在するか
         * @return プロダクトリストレスポンス
         */
        fun from(products: List<Product>, hasMore: Boolean): ProductListResponse {
            return ProductListResponse(
                products.map {
                    ProductResponse.from(it)
                },
                hasMore,
            )
        }
    }
}
