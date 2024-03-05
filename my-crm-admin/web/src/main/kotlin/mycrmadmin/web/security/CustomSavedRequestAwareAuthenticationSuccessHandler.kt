package mycrmadmin.web.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler

/**
 * カスタム認証成功ハンドラー
 */
class CustomSavedRequestAwareAuthenticationSuccessHandler : SavedRequestAwareAuthenticationSuccessHandler() {
    companion object {
        /**
         * 非アクティブの最大インターバル
         */
        const val MAX_INACTIVE_INTERVAL = 3600
    }

    init {
        setRequestCache(CustomHttpSessionRequestCache())
    }

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ) {
        // セッションの非アクティブ時の最大インターバルを設定する。
        // `authentication` から ID トークンの情報を取得できるので、それをもとに個別にインターバルを設定することも可能。
        request.getSession(false).maxInactiveInterval = MAX_INACTIVE_INTERVAL
        super.onAuthenticationSuccess(request, response, authentication)
    }
}
