package mycrmservice.core.usecase.product

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.toResultOr
import mycrmservice.core.authorization.Action
import mycrmservice.core.authorization.ActorInterface
import mycrmservice.core.authorization.Authorizer
import mycrmservice.core.authorization.allow
import mycrmservice.core.entity.Product
import mycrmservice.core.repository.ProductRepository
import java.util.UUID

/**
 * プロダクト取得
 */
class GetProduct(
    private val productRepository: ProductRepository,
    private val authorizer: Authorizer,
) {
    /**
     * プロダクトを取得する。
     *
     * @param id プロダクト ID
     * @param actor アクター
     * @return 取得に成功した場合は ID が一致するプロダクト。失敗した場合は [Error]。
     */
    fun getProductById(id: UUID, actor: ActorInterface): Result<Product, Error> {
        return productRepository.findById(id)
            .toResultOr {
                NotFound
            }
            .andThen {
                if (authorizer.allow(actor, Action.Read, it)) {
                    Ok(it)
                } else {
                    Err(Unauthorized)
                }
            }
    }

    /**
     * エラー
     */
    sealed interface Error

    /**
     * プロダクトが存在しない
     */
    data object NotFound : Error

    /**
     * 権限なし
     */
    data object Unauthorized : Error
}
