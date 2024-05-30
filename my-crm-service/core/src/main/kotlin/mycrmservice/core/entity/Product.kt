package mycrmservice.core.entity

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import java.time.OffsetDateTime
import java.util.UUID

/**
 * プロダクト
 */
data class Product(
    /**
     * テナント ID
     */
    val tenantId: UUID,
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
    /**
     * 作成日時
     */
    val created: OffsetDateTime,
    /**
     * 更新日時
     */
    val updated: OffsetDateTime,
) {
    companion object {
        /**
         * SKU の最小文字数
         */
        const val SKU_MIN_SIZE = 1

        /**
         * SKU の最大文字数
         */
        const val SKU_MAX_SIZE = 50

        /**
         * 名前の最小文字数
         */
        const val NAME_MIN_SIZE = 1

        /**
         * 名前の最大文字数
         */
        const val NAME_MAX_SIZE = 100

        /**
         * 詳細の最小文字数
         */
        const val DESCRIPTION_MIN_SIZE = 0

        /**
         * 詳細の最大文字数
         */
        const val DESCRIPTION_MAX_SIZE = 500

        /**
         * Create
         *
         * @param tenantId
         * @param id
         * @param sku
         * @param name
         * @param description
         * @param active
         * @param created
         * @param updated
         */
        fun create(
            tenantId: UUID,
            id: UUID,
            sku: String,
            name: String,
            description: String,
            active: Boolean,
            created: OffsetDateTime,
            updated: OffsetDateTime,
        ): Result<Product, String> {
            if (sku.length !in SKU_MIN_SIZE..SKU_MAX_SIZE) {
                return Err("SKU は $SKU_MIN_SIZE から $SKU_MAX_SIZE の間のサイズにしてください")
            }

            if (name.length !in NAME_MIN_SIZE..NAME_MAX_SIZE) {
                return Err("名前は $NAME_MIN_SIZE から $NAME_MAX_SIZE の間のサイズにしてください")
            }

            if (description.length !in DESCRIPTION_MIN_SIZE..DESCRIPTION_MAX_SIZE) {
                return Err("詳細は $DESCRIPTION_MIN_SIZE から $DESCRIPTION_MAX_SIZE の間のサイズにしてください")
            }

            val product = Product(
                tenantId,
                id,
                sku,
                name,
                description,
                active,
                created,
                updated,
            )

            return Ok(product)
        }
    }
}
