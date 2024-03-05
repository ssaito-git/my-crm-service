package mycrmadmin.web.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.web.savedrequest.DefaultSavedRequest
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.security.web.util.UrlUtils

/**
 * カスタム HTTP セッションリクエストキャッシュ
 */
class CustomHttpSessionRequestCache : HttpSessionRequestCache() {
    override fun saveRequest(request: HttpServletRequest, response: HttpServletResponse) {
        saveRequest(request)
    }

    /**
     * リクエストを保存する
     *
     * @param request リクエスト
     */
    fun saveRequest(request: HttpServletRequest) {
        val returnUrl = request.getParameter("return_url")
            ?: return

        if (!UrlUtils.isValidRedirectUrl(returnUrl)) {
            return
        }

        val savedRequest = DefaultSavedRequest.Builder()
            .setScheme(request.scheme)
            .setServerName(request.serverName)
            .setServerPort(request.serverPort)
            .setRequestURI(returnUrl)
            .setMethod(request.method)
            .build()

        request.session.setAttribute("SPRING_SECURITY_SAVED_REQUEST", savedRequest)
    }
}
