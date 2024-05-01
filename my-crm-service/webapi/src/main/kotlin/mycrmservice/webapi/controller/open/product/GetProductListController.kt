package mycrmservice.webapi.controller.open.product

import mycrmservice.core.authorization.DecisionService
import mycrmservice.core.repository.ProductRepository
import mycrmservice.core.usecase.GetProductList
import mycrmservice.webapi.authorization.Actor
import mycrmservice.webapi.controller.open.product.dto.ProductListResponse
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController

/**
 * プロダクトリスト取得コントローラー
 */
@RestController
class GetProductListController(
    private val productRepository: ProductRepository,
    private val decisionService: DecisionService,
) : GetProductListApi {
    @Transactional
    override fun getProductList(
        limit: Int,
        offset: Int,
        active: Boolean,
        actor: Actor,
    ): ResponseEntity<ProductListResponse> {
        val useCase = GetProductList(productRepository, decisionService)
        val products = useCase.get(actor)
        return ResponseEntity.ok(ProductListResponse.from(products, false))
    }
}
