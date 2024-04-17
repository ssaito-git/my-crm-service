plugins {
    alias(libs.plugins.springframework.boot)
    alias(libs.plugins.spring.dependency.management)
    kotlin("jvm")
    alias(libs.plugins.spring)
    `mycrmservice-detekt`
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot
    implementation(libs.spring.boot.starter.oauth2.client)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.webflux)
    implementation(libs.spring.boot.starter.aop)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.jackson.module.kotlin)
    developmentOnly(libs.spring.boot.devtools)
    testImplementation(libs.spring.boot.starter.test)
}

kotlin {
    jvmToolchain(17)
}
