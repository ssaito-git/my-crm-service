package mycrmservice.webapi.controller.open.product

import mycrmservice.core.entity.Product
import mycrmservice.core.repository.ProductRepository
import mycrmservice.webapi.authorization.Action
import mycrmservice.webapi.authorization.Actor
import mycrmservice.webapi.authorization.DecisionService
import mycrmservice.webapi.controller.open.product.dto.ProductResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

/**
 * プロダクト取得コントローラー
 */
@RestController
class GetProductController : GetProductApi {
    override fun getProductById(productId: String, actor: Actor): ResponseEntity<ProductResponse> {
        TODO("Not yet implemented")
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
