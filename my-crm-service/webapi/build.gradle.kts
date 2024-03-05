plugins {
    kotlin("jvm")
    kotlin("plugin.spring") version "1.9.22"
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.springdoc.openapi-gradle-plugin") version "1.8.0"
    `mycrmservice-detekt`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":my-crm-service:core"))
    implementation(project(":my-crm-service:data"))

    // spring boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    runtimeOnly("com.nimbusds:oauth2-oidc-sdk:10.9.1")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // jooq
    implementation("org.jooq:jooq:3.19.3")

    // open api
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.3.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
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
