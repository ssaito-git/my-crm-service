plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
    `mycrmservice-detekt`
}

repositories {
    mavenCentral()
    mavenLocal {
        content {
            includeGroup("myoidcprovider")
        }
    }
}

val logbackVersion = "1.4.14"

dependencies {
    // ktor server
    implementation(libs.ktor.server.core.jvm)
    implementation(libs.ktor.server.netty.jvm)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.sessions.jvm)
    implementation(libs.ktor.server.freemarker)
    implementation(libs.ktor.server.freemarker.jvm)
    implementation(libs.ktor.server.auth)

    // Logback
    implementation(libs.logback.classic)

    // Ktor Client
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)

    // WebAuthn4J
    implementation(libs.webauthn4j.core)

    // Jackson
    implementation(libs.jackson.databind)
    implementation(libs.jackson.dataformat.cbor)

    // myoidcprovider
    implementation(libs.myoidcprovider.ktor)
}

kotlin {
    jvmToolchain(17)
}
