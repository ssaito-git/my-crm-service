package mycrmservice.data.query

import mycrmservice.data.extension.add
import mycrmservice.data.jooq.tables.references.PRODUCTS
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.util.UUID

@Component
class ProductSearchQuery(
    private val dslContext: DSLContext,
) {
    fun execute(parameter: Parameter): QueryResult {
        val condition = createCondition(parameter)

        val count = dslContext.fetchCount(PRODUCTS, condition)

        val items = dslContext.selectFrom(PRODUCTS)
            .where(condition)
            .offset(parameter.offset)
            .limit(parameter.limit)
            .fetchArray()
            .map {
                QueryItem(
                    it.id,
                    it.sku,
                    it.name,
                    it.description,
                    it.created,
                    it.updated,
                )
            }

        val hasMore = parameter.offset + parameter.limit < count

        return QueryResult(items, hasMore)
    }

    private fun createCondition(parameter: Parameter): Condition {
        return DSL.noCondition()
            .add { c ->
                parameter.sku?.let {
                    c.and(PRODUCTS.SKU.contains(it))
                }
            }
            .add { c ->
                parameter.name?.let {
                    c.and(PRODUCTS.NAME.contains(it))
                }
            }
            .add { c ->
                parameter.active?.let {
                    c.and(PRODUCTS.ACTIVE.eq(it))
                }
            }
    }

    /**
     * 検索パラメーター
     *
     * @property sku SKU
     * @property name 名前
     * @property active プロダクトが有効かどうか
     * @property limit 取得件数
     * @property offset オフセット
     */
    data class Parameter(
        val sku: String?,
        val name: String?,
        val active: Boolean?,
        val limit: Long,
        val offset: Long,
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
