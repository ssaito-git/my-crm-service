package mycrmservice.data.query

import org.jooq.DSLContext
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.util.UUID

@Component
class ProductSearchQuery(
    private val dslContext: DSLContext,
) {
    fun execute(parameter: Parameter) {
    }

    /**
     * 検索パラメーター
     *
     * @property sku SKU
     * @property name 名前
     * @property active プロダクトが有効かどうか
     */
    data class Parameter(
        val sku: String?,
        val name: String?,
        val active: Boolean?,
    )

    data class QueryResult(
        val items: List<QueryItem>,
        val hasMore: Boolean,
    )

    data class QueryItem(
        val id: UUID,
        val sku: String,
        val name: String,
        val description: String,
        val created: OffsetDateTime,
        val updated: OffsetDateTime,
    )
}
