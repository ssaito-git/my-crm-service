package mycrmservice.webapi.controller.open.product.dto

import io.swagger.v3.oas.annotations.media.Schema

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
)
