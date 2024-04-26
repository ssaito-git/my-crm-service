package mycrmservice.webapi.controller.open.product

import mycrmservice.core.entity.Product
import mycrmservice.core.repository.ProductRepository
import mycrmservice.webapi.authorization.Actor
import mycrmservice.webapi.controller.open.product.dto.ProductListResponse
import mycrmservice.webapi.controller.open.product.dto.ProductResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

/**
 * プロダクトリスト取得コントローラー
 */
@RestController
class GetProductListController(
    private val productRepository: ProductRepository,
) : GetProductListApi {
    override fun getProductList(
        limit: Int,
        offset: Int,
        active: Boolean,
        actor: Actor,
    ): ResponseEntity<ProductListResponse> {
        val useCase = GetProductList(productRepository)
        val productList = useCase.get()
        val products = productList.map {
            ProductResponse.from(it)
        }
        return ResponseEntity.ok(ProductListResponse(products, false))
    }
}

class GetProductList(
    private val productRepository: ProductRepository,
) {
    fun get(): List<Product> {
        return productRepository.findAll()
    }
}
