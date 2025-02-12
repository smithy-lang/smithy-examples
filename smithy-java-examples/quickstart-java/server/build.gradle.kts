description = "Cafe service server implementation"

plugins {
    `java-library`
    application
    // Executes smithy-build process to generate server stubs
    id("software.amazon.smithy.gradle.smithy-base")
}

dependencies {
    val smithyJavaVersion: String by project

    // === Code generators ===
    smithyBuild("software.amazon.smithy.java.codegen:plugins:$smithyJavaVersion")

    // === Service model ===
    implementation(project(":model"))

    // === Server dependencies ===
    // Adds an HTTP server implementation based on netty
    implementation("software.amazon.smithy.java:server-netty:$smithyJavaVersion")
    // Adds the server implementation of the `RestJson1` protocol
    implementation("software.amazon.smithy.java:aws-server-restjson:$smithyJavaVersion")
}

// Add generated source code to the compilation sourceSet
afterEvaluate {
    val serverPath = smithy.getPluginProjectionPath(smithy.sourceProjection.get(), "java-server-codegen")
    sourceSets.main.get().java.srcDir(serverPath)
}

tasks.named("compileJava") {
    dependsOn("smithyBuild")
}

// Use the application plugin to start the service via the `run` task.
application {
    mainClass = "io.smithy.java.server.example.CafeService"
}
