package mycrmservice.webapi.controller.open.product

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import mycrmservice.webapi.authorization.Actor
import mycrmservice.webapi.controller.open.product.dto.PostProductRequest
import mycrmservice.webapi.controller.open.product.dto.ProductResponse
import mycrmservice.webapi.web.annnotation.CurrentActor
import mycrmservice.webapi.web.annnotation.CurrentTenantId
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.util.UUID

/**
 * プロダクトの登録 API
 */
@Tag(name = "Product", description = "")
interface PostProductApi {
    /**
     * プロダクトを登録する。
     *
     * @param body リクエストボディ
     * @return レスポンス
     */
    @Operation(
        summary = "プロダクトを登録する",
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
        ],
    )
    @PostMapping(
        "/products",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun post(
        @RequestBody @Validated body: PostProductRequest,
        @CurrentActor actor: Actor,
        @CurrentTenantId tenantId: UUID,
    ): ResponseEntity<ProductResponse>
}
