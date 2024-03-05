package mycrmauth.web.viewmodel

/**
 * サインインフォームビューモデル
 */
data class SignInFormViewModel(
    /**
     * ユーザー名
     */
    val username: String? = null,
    /**
     * ユーザー名入力エラー
     */
    val usernameInputError: String? = null,
    /**
     * パスワード入力エラー
     */
    val passwordInputError: String? = null,
    /**
     * サインインエラー
     */
    val signInError: String? = null,
)
