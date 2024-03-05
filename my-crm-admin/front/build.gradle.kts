import com.github.gradle.node.npm.task.NpxTask

plugins {
    id("com.github.node-gradle.node") version "7.0.2"
}

node {
    download = false
}

tasks.register<NpxTask>("generateOpenApiClient") {
    dependsOn(":my-crm-service:webapi:clean")
    dependsOn(":my-crm-service:webapi:generateOpenApiDocs").mustRunAfter(":my-crm-service:webapi:clean")
    mustRunAfter(":my-crm-service:webapi:generateOpenApiDocs")

    command = "orval"
}
