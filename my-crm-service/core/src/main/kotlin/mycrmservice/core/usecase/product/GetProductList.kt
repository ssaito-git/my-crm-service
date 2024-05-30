package mycrmservice.core.usecase.product

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import mycrmservice.core.authorization.Action
import mycrmservice.core.authorization.ActorInterface
import mycrmservice.core.authorization.Authorizer
import mycrmservice.core.authorization.allow
import mycrmservice.core.entity.Product
import mycrmservice.core.repository.ProductRepository

/**
 * プロダクトのリストを取得するユースケース
 */
class GetProductList(
    private val productRepository: ProductRepository,
    private val authorizer: Authorizer,
) {
    /**
     * プロダクトのリストを取得する
     */
    fun execute(actor: ActorInterface): Result<List<Product>, Error> {
        val products = productRepository.findAll()

        if (!authorizer.allow(actor, Action.Read, products)) {
            Err(Unauthorized)
        }

        return Ok(products)
    }

    /**
     * エラー
     */
    sealed interface Error

    /**
     * 権限なし
     */
    data object Unauthorized : Error
}
