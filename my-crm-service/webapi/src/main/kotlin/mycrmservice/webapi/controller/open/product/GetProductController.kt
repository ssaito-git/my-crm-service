package mycrmservice.webapi.controller.open.product

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.getOrThrow
import mycrmservice.core.authorization.Authorizer
import mycrmservice.core.repository.ProductRepository
import mycrmservice.core.usecase.product.GetProduct
import mycrmservice.webapi.authorization.Actor
import mycrmservice.webapi.controller.open.product.dto.ProductResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

/**
 * プロダクト取得コントローラー
 */
@RestController
class GetProductController(
    private val productRepository: ProductRepository,
    private val authorizer: Authorizer,
) : GetProductApi {
    @Transactional
    override fun getProductById(productId: UUID, actor: Actor): ResponseEntity<ProductResponse> {
        return GetProduct(productRepository, authorizer)
            .getProductById(productId, actor)
            .andThen {
                Ok(ResponseEntity.ok(ProductResponse.of(it)))
            }
            .getOrThrow {
                when (it) {
                    GetProduct.NotFound -> {
                        ResponseStatusException(HttpStatus.NOT_FOUND)
                    }

                    GetProduct.Unauthorized -> {
                        ResponseStatusException(HttpStatus.FORBIDDEN)
                    }
                }
            }
    }
}
