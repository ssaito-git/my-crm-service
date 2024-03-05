package mycrmservice.data.repository

import mycrmservice.core.entity.IssuerType
import mycrmservice.core.entity.TokenIntrospectionClientConfig
import mycrmservice.core.repository.TokenIntrospectionClientConfigRepository
import mycrmservice.data.jooq.tables.references.TOKEN_INTROSPECTION_CLIENT_CONFIGS
import mycrmservice.data.util.valueOfOrNull
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

/**
 * Token introspection client config repository
 */
@Repository
open class TokenIntrospectionClientConfigRepositoryImpl(
    private val context: DSLContext,
) : TokenIntrospectionClientConfigRepository {
    override fun findByResourceServerDomainName(domainName: String): TokenIntrospectionClientConfig? {
        return context.selectFrom(TOKEN_INTROSPECTION_CLIENT_CONFIGS)
            .where(TOKEN_INTROSPECTION_CLIENT_CONFIGS.RESOURCE_SERVER_DOMAIN_NAME.eq(domainName))
            .fetchOne()
            ?.let {
                TokenIntrospectionClientConfig(
                    it.tenantId,
                    it.resourceServerDomainName,
                    it.clientId,
                    it.clientSecret,
                    it.introspectionEndpoint,
                    IssuerType.entries.valueOfOrNull(it.issuerType) ?: error(""),
                )
            }
    }
}
