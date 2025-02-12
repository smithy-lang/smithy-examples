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
    smithyBuild("software.amazon.smithy.java.codegen:plugins:$smithyJavaVersion")

    // === Service model ===
    implementation(project(":model"))

    // === Client Dependencies ===
    // Core client dependency required by generated client code.
    implementation("software.amazon.smithy.java:client-core:$smithyJavaVersion")
    // Add client implementation of `RestJson1` protocol
    implementation("software.amazon.smithy.java:aws-client-restjson:$smithyJavaVersion")
}

// Add generated client source code to the main sourceSet
afterEvaluate {
    val clientPath = smithy.getPluginProjectionPath(smithy.sourceProjection.get(), "java-client-codegen")
    sourceSets.main.get().java.srcDir(clientPath)
}

tasks.named("compileJava") {
    dependsOn("smithyBuild")
}

application {
    mainClass = "io.smithy.java.client.example.ClientEntrypoint"
}
