package mycrmservice.core.usecase

import mycrmservice.core.authorization.Action
import mycrmservice.core.authorization.ActorInterface
import mycrmservice.core.authorization.DecisionService
import mycrmservice.core.entity.Product
import mycrmservice.core.repository.ProductRepository

/**
 * プロダクトのリストを取得するユースケース
 */
class GetProductList(
    private val productRepository: ProductRepository,
    private val decisionService: DecisionService,
) {
    /**
     * プロダクトのリストを取得する
     */
    fun get(actor: ActorInterface): List<Product> {
        val products = productRepository.findAll()

        if (!decisionService.allow(actor, Action.Read, products)) {
            error("")
        }

        return products
    }
}
