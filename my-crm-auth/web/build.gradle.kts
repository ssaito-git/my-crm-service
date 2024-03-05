plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.9.0"
    id("io.ktor.plugin") version "2.3.8"
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
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.ktor:ktor-server-sessions-jvm")
    implementation("io.ktor:ktor-server-freemarker")
    implementation("io.ktor:ktor-server-freemarker-jvm")
    implementation("io.ktor:ktor-server-auth")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // ktor client
    implementation("io.ktor:ktor-client-core")
    implementation("io.ktor:ktor-client-cio")
    implementation("io.ktor:ktor-client-content-negotiation")

    // webauthn
    implementation("com.webauthn4j:webauthn4j-core:0.21.7.RELEASE")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.3")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-cbor:2.15.3")

    // myoidcprovider
    implementation("myoidcprovider:ktor:0.0.1")
}

kotlin {
    jvmToolchain(17)
}
