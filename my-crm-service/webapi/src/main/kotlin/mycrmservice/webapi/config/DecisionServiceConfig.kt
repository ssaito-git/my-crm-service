package mycrmservice.webapi.config

import mycrmservice.core.authorization.DecisionService
import mycrmservice.webapi.authorization.DecisionFunction
import mycrmservice.webapi.authorization.DecisionServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * 認可判定サービスのコンフィグ
 */
@Configuration
class DecisionServiceConfig {
    /**
     * 認可判定サービスを作成する。
     *
     * @param decisionFunctions 認可判定ファンクション
     * @return 認可判定サービス
     */
    @Bean
    fun decisionService(decisionFunctions: List<DecisionFunction<*, *, *>>): DecisionService {
        val decisionService = DecisionServiceImpl()
        for (decisionFunction in decisionFunctions) {
            decisionService.addDecisionFunction(decisionFunction)
        }
        return decisionService
    }
}
