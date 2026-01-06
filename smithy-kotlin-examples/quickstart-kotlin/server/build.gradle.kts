description = "Coffee shop service server implementation"

plugins {
    `java-library`
    application
    // Executes smithy-build process to generate server stubs
    id("software.amazon.smithy.gradle.smithy-base")
}

dependencies {
    // Code generators
    smithyBuild(libs.smithy.java.plugins)

    // Service model
    implementation(project(":smithy"))

    // Server dependencies
    // Adds an HTTP server implementation based on netty
    implementation(libs.smithy.java.server.netty)
    // Adds a server implementation of the `RestJson1` protocol
    implementation(libs.smithy.java.aws.server.restjson)
    // Adds a server implementation of the `Rpcv2Cbor` protocol
    implementation(libs.smithy.java.aws.server.rpcv2.cbor)
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
    mainClass = "io.smithy.kotlin.server.example.CoffeeShopService"
}

