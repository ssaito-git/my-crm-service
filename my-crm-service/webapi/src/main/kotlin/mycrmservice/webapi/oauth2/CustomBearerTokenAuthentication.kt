package mycrmservice.webapi.oauth2

import mycrmservice.webapi.authorization.ActorType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication
import java.util.*

/**
 * ベアラートークンのカスタム認証トークン
 */
class CustomBearerTokenAuthentication(
    /**
     * プリンシパル
     */
    principal: OAuth2AuthenticatedPrincipal,
    /**
     * アクセストークン
     */
    credentials: OAuth2AccessToken,
    /**
     * 付与された権限のコレクション
     */
    authorities: Collection<GrantedAuthority>,
    /**
     * テナント ID
     */
    val tenantId: UUID,
    /**
     * アクタータイプ
     */
    val actorType: ActorType,
) : BearerTokenAuthentication(principal, credentials, authorities)
