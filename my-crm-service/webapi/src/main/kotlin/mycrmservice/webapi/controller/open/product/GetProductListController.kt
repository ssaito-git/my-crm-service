package mycrmservice.webapi.controller.open.product

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.Min
import mycrmservice.core.entity.Product
import mycrmservice.core.repository.ProductRepository
import mycrmservice.webapi.authorization.Actor
import mycrmservice.webapi.web.annnotation.CurrentActor
import org.hibernate.validator.constraints.Range
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * プロダクトリスト取得コントローラー
 */
@RestController
@Tag(name = "Product", description = "")
class GetProductListController(
    private val productRepository: ProductRepository,
) {
    /**
     * プロダクトのリストを取得する。
     *
     * @param limit 取得する件数
     * @param offset オフセット
     * @param active 有効なプロダクトのみ、または、無効なプロダクトも含めてリストを返すか
     * @param actor アクター
     * @return レスポンス
     */
    @Operation(
        summary = "プロダクトのリストを取得する",
        description = "",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "",
                content = [Content(schema = Schema(implementation = ProductListResponse::class))],
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
    @Transactional
    @GetMapping("/products", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getProductList(
        @Parameter(
            description = "取得するプロダクトの件数",
        ) @RequestParam(defaultValue = "10", required = false) @Range(min = 1, max = 50) limit: Int,
        @Parameter(
            description = "オフセット",
        ) @RequestParam(defaultValue = "0", required = false) @Min(0) offset: Int,
        @Parameter(
            description = "有効なプロダクトのみ、または、無効なプロダクトも含めてリストを返す（`false` の場合は無効なプロダクトを含む）",
        ) @RequestParam(defaultValue = "false", required = false) active: Boolean,
        @CurrentActor actor: Actor,
    ): ResponseEntity<ProductListResponse> {
        val useCase = GetProductList(productRepository)
        val productList = useCase.get()
        val products = productList.map {
            ProductResponse.from(it)
        }
        return ResponseEntity.ok(ProductListResponse(products, false))
    }
}

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

class GetProductList(
    private val productRepository: ProductRepository,
) {
    fun get(): List<Product> {
        return productRepository.findAll()
    }
}
