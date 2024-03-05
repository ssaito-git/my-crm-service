package mycrmauth.web.session

import io.ktor.server.auth.Principal
import java.util.UUID

/**
 * サインインの状態を管理するセッション
 */
data class SignInSession(
    /**
     * ユーザーの ID
     */
    val id: UUID,
) : Principal
