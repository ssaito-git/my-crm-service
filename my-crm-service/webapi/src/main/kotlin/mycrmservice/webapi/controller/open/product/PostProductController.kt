package mycrmservice.webapi.controller.open.product

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.getOrThrow
import mycrmservice.core.authorization.Authorizer
import mycrmservice.core.repository.ProductRepository
import mycrmservice.core.usecase.product.CreateProduct
import mycrmservice.webapi.authorization.Actor
import mycrmservice.webapi.controller.open.product.dto.PostProductRequest
import mycrmservice.webapi.controller.open.product.dto.ProductResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.time.Clock
import java.util.UUID

/**
 * プロダクトの登録 API 実装
 */
@RestController
class PostProductController(
    private val productRepository: ProductRepository,
    private val clock: Clock,
    private val authorizer: Authorizer,
) : PostProductApi {
    @Transactional
    override fun post(body: PostProductRequest, actor: Actor, tenantId: UUID): ResponseEntity<ProductResponse> {
        val parameter = CreateProduct.Parameter(
            tenantId,
            body.sku,
            body.name,
            body.description,
            body.active,
        )

        return CreateProduct(productRepository, clock, authorizer)
            .execute(parameter, actor)
            .andThen {
                Ok(ResponseEntity.ok(ProductResponse.of(it)))
            }
            .getOrThrow {
                when (it) {
                    CreateProduct.AlreadyExistsError -> {
                        ResponseStatusException(HttpStatus.BAD_REQUEST, "同じ値の SKU が既に存在します")
                    }

                    is CreateProduct.ValidationError -> {
                        ResponseStatusException(HttpStatus.BAD_REQUEST, it.message)
                    }

                    CreateProduct.Unauthorized -> {
                        ResponseStatusException(HttpStatus.FORBIDDEN)
                    }
                }
            }
    }
}
