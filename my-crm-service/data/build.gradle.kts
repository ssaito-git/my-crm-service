import org.flywaydb.gradle.task.FlywayMigrateTask

plugins {
    kotlin("jvm")
    `java-library`
    alias(libs.plugins.flyway)
    alias(libs.plugins.jooq.codegen.gradle)
    `mycrmservice-detekt`
}

buildscript {
    dependencies {
        classpath(libs.postgresql)
        classpath(libs.flyway.database.postgresql)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":my-crm-service:core"))

    // jOOQ
    implementation(libs.jooq)

    // PostgreSQL JDBC Driver
    implementation(libs.postgresql)

    // Spring アノテーションを利用するために追加
    implementation(libs.spring.context)

    // Flyway の Java-based Migrations を利用するために追加
    testImplementation(libs.flyway.core)

    // JUnit
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
}

sourceSets {
    main {
        kotlin {
            val outputDirectory = layout.buildDirectory.dir("jooq")
            srcDir(outputDirectory.get().asFile)
        }
    }
}

flyway {
    url = "jdbc:postgresql://localhost:5432/crm_service"
    user = "postgres"
    password = "secret"
    cleanDisabled = false
    defaultSchema = "crm_service_schema"
}

jooq {
    configuration {
        jdbc {
            url = "jdbc:postgresql://localhost:5432/crm_service"
            user = "postgres"
            password = "secret"
        }
        generator {
            name = "org.jooq.codegen.KotlinGenerator"
            target {
                val outputDirectory = layout.buildDirectory.dir("jooq")
                packageName = "mycrmservice.data.jooq"
                directory = outputDirectory.get().asFile.path
            }
            database {
                name = "org.jooq.meta.postgres.PostgresDatabase"
                inputSchema = "crm_service_schema"
                // Flyway が作成するバージョン管理テーブルは除外する
                excludes = "flyway_schema_history"
            }
            generate {
                isKotlinNotNullPojoAttributes = true
                isKotlinNotNullRecordAttributes = true
                isKotlinNotNullInterfaceAttributes = true
            }
        }
    }
}

tasks.register<FlywayMigrateTask>("flywayMigrateDevelop") {
    dependsOn("compileTestKotlin")
    dependsOn("processTestResources")
    dependsOn("flywayMigrate")
    tasks["compileTestKotlin"].mustRunAfter("flywayMigrate")
    tasks["processTestResources"].mustRunAfter("compileTestKotlin")
    tasks["flywayMigrateDevelop"].mustRunAfter("processTestResources")

    url = "jdbc:postgresql://localhost:5432/crm_service"
    user = "postgres"
    password = "secret"
    locations = arrayOf("classpath:/testdata")
    defaultSchema = "crm_service_schema"
}
