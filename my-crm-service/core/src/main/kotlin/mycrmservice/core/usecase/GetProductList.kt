package mycrmservice.core.usecase

import mycrmservice.core.entity.Product
import mycrmservice.core.repository.ProductRepository

/**
 * プロダクトのリストを取得するユースケース
 */
class GetProductList(
    private val productRepository: ProductRepository,
) {
    /**
     * プロダクトのリストを取得する
     */
    fun get(): List<Product> {
        return productRepository.findAll()
    }
}
