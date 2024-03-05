import org.flywaydb.gradle.task.FlywayMigrateTask

plugins {
    kotlin("jvm")
    `java-library`
//    id("org.springframework.boot") version "3.2.1"
//    id("io.spring.dependency-management") version "1.1.3"
    id("org.flywaydb.flyway") version "10.6.0"
    id("org.jooq.jooq-codegen-gradle") version "3.19.3"
    `mycrmservice-detekt`
}

buildscript {
    dependencies {
        classpath("org.postgresql:postgresql:42.7.1")
        classpath("org.flywaydb:flyway-database-postgresql:10.6.0")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":my-crm-service:core"))
    implementation("org.jooq:jooq:3.19.3")
    implementation("org.postgresql:postgresql:42.7.1")
    implementation("org.springframework:spring-context:6.1.3")

    // Flyway の Java-based Migrations を利用するために追加
    testImplementation("org.flywaydb:flyway-core:10.6.0")

    testImplementation(platform("org.junit:junit-bom:5.10.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
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
