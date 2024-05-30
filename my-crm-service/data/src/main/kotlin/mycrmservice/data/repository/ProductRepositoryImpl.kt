package mycrmservice.data.repository

import mycrmservice.core.entity.Product
import mycrmservice.core.repository.ProductRepository
import mycrmservice.data.jooq.tables.references.PRODUCTS
import org.jooq.DSLContext
import org.jooq.impl.DSL.excluded
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
                    it.tenantId,
                    it.id,
                    it.sku,
                    it.name,
                    it.description,
                    it.active,
                    it.created,
                    it.updated,
                )
            }
    }

    override fun findBySku(sku: String): Product? {
        return context.selectFrom(PRODUCTS)
            .where(PRODUCTS.SKU.eq(sku))
            .fetchOne()
            ?.let {
                Product(
                    it.tenantId,
                    it.id,
                    it.sku,
                    it.name,
                    it.description,
                    it.active,
                    it.created,
                    it.updated,
                )
            }
    }

    override fun findAll(): List<Product> {
        return context.selectFrom(PRODUCTS)
            .fetchArray()
            .map {
                Product(
                    it.tenantId,
                    it.id,
                    it.sku,
                    it.name,
                    it.description,
                    it.active,
                    it.created,
                    it.updated,
                )
            }
    }

    override fun save(product: Product) {
        context
            .insertInto(
                PRODUCTS,
                PRODUCTS.TENANT_ID,
                PRODUCTS.ID,
                PRODUCTS.SKU,
                PRODUCTS.NAME,
                PRODUCTS.DESCRIPTION,
                PRODUCTS.ACTIVE,
                PRODUCTS.CREATED,
                PRODUCTS.UPDATED,
            )
            .values(
                product.tenantId,
                product.id,
                product.sku,
                product.name,
                product.description,
                product.active,
                product.created,
                product.updated,
            )
            .onConflict(
                PRODUCTS.TENANT_ID,
                PRODUCTS.ID,
            )
            .doUpdate()
            .set(PRODUCTS.NAME, excluded(PRODUCTS.NAME))
            .set(PRODUCTS.DESCRIPTION, excluded(PRODUCTS.DESCRIPTION))
            .set(PRODUCTS.ACTIVE, excluded(PRODUCTS.ACTIVE))
            .set(PRODUCTS.CREATED, excluded(PRODUCTS.CREATED))
            .set(PRODUCTS.UPDATED, excluded(PRODUCTS.UPDATED))
            .execute()
    }
}
