plugins {
    kotlin("jvm")
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    // kotlin-result
    api(libs.kotlin.result)
}

kotlin {
    jvmToolchain(17)
}
