package mycrmservice.webapi.controller.open.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import mycrmservice.webapi.authorization.Actor
import mycrmservice.webapi.web.annnotation.CurrentActor
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * サインインユーザー取得コントローラー
 */
@RestController
@Tag(name = "Users", description = "")
class GetSignInUserController {
    /**
     * サインインユーザーを取得する。
     *
     * @param actor アクター
     * @return レスポンス
     */
    @Operation(
        summary = "サインインユーザーを取得する",
        description = "",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "",
                content = [Content(schema = Schema(implementation = UserResponse::class))],
            ),
            ApiResponse(
                responseCode = "400",
                description = "",
                content = [Content()],
            ),
        ],
    )
    @GetMapping("/users/me", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getMe(
        @CurrentActor actor: Actor,
    ): ResponseEntity<UserResponse> {
        if (actor !is Actor.ServiceUser) {
            return ResponseEntity.badRequest().build()
        }

        TODO()
    }
}

/**
 * ユーザーレスポンス
 */
@Schema(name = "User")
data class UserResponse(
    /**
     * ID
     */
    @Schema(required = true, description = "ユーザーごとに一意な ID")
    val id: String,
    /**
     * 名前
     */
    @Schema(required = true, description = "名前")
    val name: String,
    /**
     * メールアドレス
     */
    @Schema(required = true, description = "メールアドレス")
    val email: String,
)
