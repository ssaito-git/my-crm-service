package mycrmservice.webapi.controller.open.product.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import mycrmservice.core.entity.Product

/**
 * プロダクトの作成リクエスト
 */
data class PostProductRequest(
    /**
     * SKU
     */
    @Schema(required = true, description = "SKU")
    @field:NotBlank
    @field:Size(min = Product.SKU_MIN_SIZE, max = Product.SKU_MAX_SIZE)
    @field:Pattern(regexp = "^[a-zA-Z0-9_.]*$")
    val sku: String,

    /**
     * 名前
     */
    @Schema(required = true, description = "名前")
    @field:NotBlank
    @field:Size(min = Product.NAME_MIN_SIZE, max = Product.NAME_MAX_SIZE)
    val name: String,

    /**
     * 詳細
     */
    @Schema(required = true, description = "詳細")
    @field:Size(min = Product.DESCRIPTION_MIN_SIZE, max = Product.DESCRIPTION_MAX_SIZE)
    val description: String,

    /**
     * プロダクトが有効かどうか
     */
    @Schema(required = true, description = "プロダクトが有効かどうか")
    val active: Boolean,
)
