package mycrmservice.core.usecase.product

import com.fasterxml.uuid.Generators
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.mapError
import mycrmservice.core.authorization.Action
import mycrmservice.core.authorization.ActorInterface
import mycrmservice.core.authorization.Authorizer
import mycrmservice.core.authorization.allow
import mycrmservice.core.entity.Product
import mycrmservice.core.repository.ProductRepository
import java.time.Clock
import java.time.OffsetDateTime
import java.util.UUID

/**
 * プロダクト作成
 */
class CreateProduct(
    private val productRepository: ProductRepository,
    private val clock: Clock,
    private val authorizer: Authorizer,
) {
    /**
     * 実行
     *
     * @param parameter パラメーター
     * @param actor アクター
     * @return
     */
    fun execute(parameter: Parameter, actor: ActorInterface): Result<Product, Error> {
        val nowDateTime = OffsetDateTime.now(clock)

        return Product.create(
            parameter.tenantId,
            Generators.timeBasedEpochGenerator().generate(),
            parameter.sku,
            parameter.name,
            parameter.description,
            parameter.active,
            nowDateTime,
            nowDateTime,
        ).mapError {
            ValidationError(it)
        }.andThen {
            if (authorizer.allow(actor, Action.Write, it)) {
                Ok(it)
            } else {
                Err(Unauthorized)
            }
        }.andThen {
            if (productRepository.findBySku(it.sku) == null) {
                Ok(it)
            } else {
                Err(AlreadyExistsError)
            }
        }.andThen {
            productRepository.save(it)
            Ok(it)
        }
    }

    /**
     * パラメーター
     *
     * @property tenantId テナント ID
     * @property sku SKY
     * @property name 名前
     * @property description 詳細
     * @property active プロダクトが有効かどうか
     */
    data class Parameter(
        val tenantId: UUID,
        val sku: String,
        val name: String,
        val description: String,
        val active: Boolean,
    )

    /**
     * エラー
     */
    sealed interface Error

    /**
     * バリデーションエラー
     *
     * @property message メッセージ
     */
    data class ValidationError(val message: String) : Error

    /**
     * SKU がすでに存在するエラー
     */
    data object AlreadyExistsError : Error

    /**
     * 権限なし
     */
    data object Unauthorized : Error
}
