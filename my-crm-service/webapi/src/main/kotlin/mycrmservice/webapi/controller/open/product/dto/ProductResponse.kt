package mycrmservice.webapi.controller.open.product.dto

import io.swagger.v3.oas.annotations.media.Schema
import mycrmservice.core.entity.Product
import mycrmservice.webapi.util.toEpochMilliSecond

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
    /**
     * プロダクトの作成日時（Unix time）ミリ秒
     */
    @Schema(required = true, description = "プロダクトの作成日時（Unix time）ミリ秒")
    val created: Long,
    /**
     * プロダクトの更新日時（Unix time）ミリ秒
     */
    @Schema(required = true, description = "プロダクトの更新日時（Unix time）ミリ秒")
    val updated: Long,
) {
    companion object {
        /**
         * プロダクトからレスポンスを作成します。
         *
         * @param product プロダクト
         * @return レスポンス
         */
        fun of(product: Product): ProductResponse {
            return ProductResponse(
                product.id.toString(),
                product.sku,
                product.name,
                product.description,
                product.active,
                product.created.toEpochMilliSecond(),
                product.updated.toEpochMilliSecond(),
            )
        }
    }
}
