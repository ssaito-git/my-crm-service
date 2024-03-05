package mycrmadmin.web.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * ログインステータスコントローラー
 */
@RestController
class LoginStatusController {
    /**
     * Login status
     *
     * @return
     */
    @GetMapping("/login/status")
    fun loginStatus(): ResponseEntity<Void> {
        return ResponseEntity.noContent().build()
    }
}
