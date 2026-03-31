description = "Cafe service client"

plugins {
    `java-library`
    application
    // Executes smithy-build process to generate client code
    id("software.amazon.smithy.gradle.smithy-base")
}

dependencies {
    val smithyJavaVersion: String by project

    // === Code generators ===
    smithyBuild("software.amazon.smithy.java:codegen-plugin:$smithyJavaVersion")

    // === Service model ===
    implementation(project(":smithy"))

    // === Client Dependencies ===
    // Core client dependency required by generated client code.
    implementation("software.amazon.smithy.java:client-core:$smithyJavaVersion")
    // Add client implementation of `RestJson1` protocol
    implementation("software.amazon.smithy.java:aws-client-restjson:$smithyJavaVersion")
}

// Add generated client source code to the main sourceSet
afterEvaluate {
    val clientPath = smithy.getPluginProjectionPath(smithy.sourceProjection.get(), "java-codegen").get()
    sourceSets {
        main {
            java {
                srcDir("$clientPath/java")
            }
            resources {
                srcDir("$clientPath/resources")
            }
        }
    }
}

tasks.named("compileJava") {
    dependsOn("smithyBuild")
}

tasks.named("processResources") {
    dependsOn("smithyBuild")
}

application {
    mainClass = "io.smithy.java.client.example.Main"
}
