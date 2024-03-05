package mycrmservice.webapi.controller.open.product

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import mycrmservice.core.entity.Product
import mycrmservice.core.repository.ProductRepository
import mycrmservice.webapi.authorization.Action
import mycrmservice.webapi.authorization.Actor
import mycrmservice.webapi.authorization.DecisionService
import mycrmservice.webapi.web.annnotation.CurrentActor
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

/**
 * プロダクト取得コントローラー
 */
@RestController
@Tag(name = "Product", description = "")
class GetProductController {
    /**
     * ID に一致するプロダクトを取得する。
     *
     * @param productId プロダクト ID
     * @param actor アクター
     * @return レスポンス
     */
    @Operation(
        summary = "ID に一致するプロダクトを取得する",
        description = "",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "",
                content = [Content(schema = Schema(implementation = ProductResponse::class))],
            ),
            ApiResponse(
                responseCode = "400",
                description = "",
                content = [Content()],
            ),
            ApiResponse(
                responseCode = "404",
                description = "",
                content = [Content()],
            ),
        ],
    )
    @GetMapping("/products/{productId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getProductById(
        @Parameter(description = "Product ID", required = true) @PathVariable productId: String,
        @CurrentActor actor: Actor,
    ): ResponseEntity<ProductResponse> {
        TODO()
    }
}

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

class GetProduct(
    private val productRepository: ProductRepository,
    private val decisionService: DecisionService,
) {
    fun get(actor: Actor, id: UUID): Product? {
        val product = productRepository.findById(id)
            ?: return null

        if (!decisionService.allow(actor, Action.Read, product)) {
            return null
        }

        return product
    }
}
