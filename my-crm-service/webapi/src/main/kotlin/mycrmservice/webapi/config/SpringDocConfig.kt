package mycrmservice.webapi.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import mycrmservice.webapi.web.annnotation.CurrentActor
import org.springdoc.core.models.GroupedOpenApi
import org.springdoc.core.utils.SpringDocUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * springdoc-openapi のコンフィグ
 */
@Configuration
@OpenAPIDefinition(info = Info(title = "My CRM Service API", description = ""))
class SpringDocConfig {
    companion object {
        init {
            SpringDocUtils.getConfig()
                .addAnnotationsToIgnore(CurrentActor::class.java)
        }
    }

    /**
     * 公開 API
     */
    @Bean
    fun openOpenApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("spec")
            .packagesToScan("mycrmservice.webapi.controller.open")
            .build()
    }

    /**
     * 内部向け API
     */
    @Bean
    fun internalOpenApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("internal")
            .packagesToScan("mycrmservice.webapi.controller.internal")
            .build()
    }
}
