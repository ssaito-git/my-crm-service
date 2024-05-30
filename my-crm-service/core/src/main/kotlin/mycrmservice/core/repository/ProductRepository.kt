package mycrmservice.core.repository

import mycrmservice.core.entity.Product
import java.util.UUID

/**
 * プロダクトリポジトリ
 */
interface ProductRepository {
    /**
     * ID で検索する。
     *
     * @param id ID
     * @return ID に一致する [Product]。存在しない場合は null。
     */
    fun findById(id: UUID): Product?

    /**
     * SKU で検索する。
     *
     * @param sku SKU
     * @return SKU に一致する [Product]。存在しない場合は null。
     */
    fun findBySku(sku: String): Product?

    /**
     * すべてのプロダクトを取得する。
     *
     * @return プロダクトのリスト
     */
    fun findAll(): List<Product>

    /**
     * プロダクトを保存する。
     *
     * @param product プロダクト
     */
    fun save(product: Product)
}
