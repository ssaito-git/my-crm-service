package mycrmservice.webapi.controller.open.product

import com.github.michaelbull.result.getOrThrow
import mycrmservice.core.authorization.Authorizer
import mycrmservice.core.repository.ProductRepository
import mycrmservice.core.usecase.product.GetProductList
import mycrmservice.webapi.authorization.Actor
import mycrmservice.webapi.controller.open.product.dto.ProductListResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

/**
 * プロダクトリスト取得コントローラー
 */
@RestController
class GetProductListController(
    private val productRepository: ProductRepository,
    private val authorizer: Authorizer,
) : GetProductListApi {
    @Transactional
    override fun getProductList(
        limit: Int,
        offset: Int,
        active: Boolean,
        actor: Actor,
    ): ResponseEntity<ProductListResponse> {
        val useCase = GetProductList(productRepository, authorizer)
        val products = useCase.get(actor)
            .getOrThrow {
                when (it) {
                    GetProductList.Unauthorized -> ResponseStatusException(HttpStatus.UNAUTHORIZED)
                }
            }

        return ResponseEntity.ok(ProductListResponse.from(products, false))
    }
}
