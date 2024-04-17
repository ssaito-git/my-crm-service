plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.23")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.6")
}
