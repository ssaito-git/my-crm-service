package mycrmadmin.web.repository

import org.springframework.stereotype.Repository

/**
 * リソースサーバー
 */
data class ResourceServer(
    /**
     * クライアント登録 ID
     */
    val clientRegistrationId: String,
    /**
     * ベース URL
     */
    val baseUrl: String,
)

/**
 * リソースサーバーリポジトリ
 */
@Repository
class ResourceServerRepository {
    private val resourceServerList = listOf(
        ResourceServer("127.0.0.1", "http://127.0.0.1:8090"),
    )

    /**
     * クライアント登録 ID でリソースサーバーを取得する。
     *
     * @param clientRegistrationId クライアント登録 ID
     * @return クライアント登録 ID に一致する [ResourceServer]。存在しない場合は null。
     */
    fun findByClientRegistrationId(clientRegistrationId: String): ResourceServer? {
        return resourceServerList.firstOrNull { it.clientRegistrationId == clientRegistrationId }
    }
}
