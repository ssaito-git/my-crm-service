package mycrmservice.core.entity

import java.util.*

/**
 * トークンイントロスペクションクライアント設定
 */
class TokenIntrospectionClientConfig(
    /**
     * テナント ID
     */
    val tenantId: UUID,
    /**
     * リソースサーバーのドメイン名
     */
    val resourceServerDomainName: String,
    /**
     * クライアント ID
     */
    val clientId: String,
    /**
     * クライアントシークレット
     */
    val clientSecret: String,
    /**
     * イントロスペクションエンドポイント
     */
    val introspectionEndpoint: String,
    /**
     * Issuer タイプ
     */
    val issuerType: IssuerType,
)

/**
 * Issuer タイプ
 */
enum class IssuerType {
    SERVICE,
    SYSTEM,
}
