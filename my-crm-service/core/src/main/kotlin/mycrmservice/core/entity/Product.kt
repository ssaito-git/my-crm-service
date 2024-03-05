package mycrmservice.core.entity

import java.util.UUID

/**
 * プロダクト
 */
class Product(
    /**
     * ID
     */
    val id: UUID,
    /**
     * SKU
     */
    val sku: String,
    /**
     * 名前
     */
    val name: String,
    /**
     * 詳細
     */
    val description: String,
    /**
     * プロダクトが有効かどうか
     */
    val active: Boolean,
)
