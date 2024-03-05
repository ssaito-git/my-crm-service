package mycrmservice.core.repository

import mycrmservice.core.entity.TokenIntrospectionClientConfig

/**
 * トークンイントロスペクションクライアント設定のリポジトリ
 */
interface TokenIntrospectionClientConfigRepository {
    /**
     * ドメイン名で検索する。
     *
     * @param domainName ドメイン名
     * @return ドメイン名に一致する [TokenIntrospectionClientConfig]。存在しない場合は null。
     */
    fun findByResourceServerDomainName(domainName: String): TokenIntrospectionClientConfig?
}
