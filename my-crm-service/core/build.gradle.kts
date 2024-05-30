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

    // java-uuid-generator
    implementation(libs.java.uuid.generator)
}

kotlin {
    jvmToolchain(17)
}
