package mycrmservice.webapi.controller.open.product

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import mycrmservice.webapi.authorization.Actor
import mycrmservice.webapi.controller.open.product.dto.ProductResponse
import mycrmservice.webapi.web.annnotation.CurrentActor
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.UUID

/**
 * プロダクト取得 API
 */
@Tag(name = "Product", description = "")
interface GetProductApi {
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
        @Parameter(description = "Product ID", required = true) @PathVariable productId: UUID,
        @CurrentActor actor: Actor,
    ): ResponseEntity<ProductResponse>
}
