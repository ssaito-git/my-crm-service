package mycrmservice.webapi.config

import mycrmservice.core.authorization.Authorizer
import mycrmservice.webapi.authorization.AuthorizerImpl
import mycrmservice.webapi.authorization.DecisionFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * 認可判定サービスのコンフィグ
 */
@Configuration
class AuthorizerConfig {
    /**
     * 認可判定サービスを作成する。
     *
     * @param decisionFunctions 認可判定ファンクション
     * @return 認可判定サービス
     */
    @Bean
    fun authorizer(decisionFunctions: List<DecisionFunction<*, *>>): Authorizer {
        val authorizer = AuthorizerImpl()
        for (decisionFunction in decisionFunctions) {
            authorizer.add(decisionFunction)
        }
        return authorizer
    }
}
