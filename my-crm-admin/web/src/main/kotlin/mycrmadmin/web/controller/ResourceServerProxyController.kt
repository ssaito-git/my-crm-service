package mycrmadmin.web.controller

import jakarta.servlet.http.HttpServletRequest
import mycrmadmin.web.repository.ResourceServerRepository
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.toEntity
import reactor.core.publisher.Mono

/**
 * リソースサーバープロキシコントローラー
 */
@RestController
class ResourceServerProxyController(
    private val webClient: WebClient,
    private val resourceServerRepository: ResourceServerRepository,
) {
    companion object {
        private val ALLOWED_HEADER_NAMES = arrayOf(HttpHeaders.CONTENT_TYPE)
    }

    /**
     * リソースサーバーへのリクエストを処理する。
     *
     * @param body リクエストボディ
     * @param httpMethod HTTP メソッド
     * @param request リクエスト
     * @param authorizedClient 認可クライアント
     * @return レスポンス
     */
    @RequestMapping("/api/**")
    fun handle(
        @RequestBody body: String?,
        httpMethod: HttpMethod,
        request: HttpServletRequest,
        @RegisteredOAuth2AuthorizedClient authorizedClient: OAuth2AuthorizedClient,
    ): Mono<ResponseEntity<Any>> {
        val resourceServer =
            resourceServerRepository.findByClientRegistrationId(authorizedClient.clientRegistration.registrationId)
                ?: return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build())

        val apiRequest = webClient.method(httpMethod)
            .uri("${resourceServer.baseUrl}${request.requestURI.removePrefix("/api")}")
            .attributes(ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(authorizedClient))
            .headers {
                for (headerName in request.headerNames) {
                    if (ALLOWED_HEADER_NAMES.contains(headerName)) {
                        it.addAll(headerName, request.getHeaders(headerName).toList())
                    }
                }
            }

        return if (body != null) {
            apiRequest.bodyValue(body).exchangeToMono {
                it.toEntity<Any>()
            }
        } else {
            apiRequest.exchangeToMono {
                it.toEntity<Any>()
            }
        }
    }
}
