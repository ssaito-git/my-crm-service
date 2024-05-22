plugins {
    kotlin("jvm")
    alias(libs.plugins.spring)
    alias(libs.plugins.springframework.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.openapi.gradle.plugin)
    `mycrmservice-detekt`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":my-crm-service:core"))
    implementation(project(":my-crm-service:data"))

    // Spring Boot
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.oauth2.resource.server)
    implementation(libs.spring.boot.starter.aop)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.jdbc)
    developmentOnly(libs.spring.boot.devtools)
    testImplementation(libs.spring.boot.starter.test)

    // Jackson
    implementation(libs.jackson.module.kotlin)
    implementation(libs.jackson.databind.nullable)

    // Nimbus
    runtimeOnly(libs.oauth2.oidc.sdk)

    // jOOQ
    implementation(libs.jooq)

    // PostgreSQL JDBC Driver
    runtimeOnly(libs.postgresql)

    // Springdoc-openapi
    implementation(libs.springdoc.openapi.starter.webmvc.api)
}

kotlin {
    jvmToolchain(17)
}

openApi {
    apiDocsUrl = "http://localhost:8090/v3/api-docs"
    groupedApiMappings = mapOf(
        "http://localhost:8090/v3/api-docs/spec" to "openapi-open.json",
        "http://localhost:8090/v3/api-docs/internal" to "openapi-internal.json",
    )
}
