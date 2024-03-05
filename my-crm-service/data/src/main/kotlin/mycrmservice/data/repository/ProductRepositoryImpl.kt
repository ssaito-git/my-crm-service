package mycrmservice.data.repository

import mycrmservice.core.entity.Product
import mycrmservice.core.repository.ProductRepository
import mycrmservice.data.jooq.tables.references.PRODUCTS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.util.UUID

/**
 * プロダクトリポジトリの実装
 */
@Repository
open class ProductRepositoryImpl(private val context: DSLContext) : ProductRepository {
    override fun findById(id: UUID): Product? {
        return context.selectFrom(PRODUCTS)
            .where(PRODUCTS.ID.eq(id))
            .fetchOne()
            ?.let {
                Product(
                    it.id,
                    it.sku,
                    it.name,
                    it.description,
                    it.active,
                )
            }
    }

    override fun findBySku(sku: String): Product? {
        return context.selectFrom(PRODUCTS)
            .where(PRODUCTS.SKU.eq(sku))
            .fetchOne()
            ?.let {
                Product(
                    it.id,
                    it.sku,
                    it.name,
                    it.description,
                    it.active,
                )
            }
    }

    override fun findAll(): List<Product> {
        return context.selectFrom(PRODUCTS)
            .fetchArray()
            .map {
                Product(
                    it.id,
                    it.sku,
                    it.name,
                    it.description,
                    it.active,
                )
            }
    }
}
