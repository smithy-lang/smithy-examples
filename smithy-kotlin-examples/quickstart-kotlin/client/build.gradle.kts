description = "Coffee shop service client"

plugins {
    application
    kotlin("jvm") version "2.3.0"
    // Executes smithy-build process to generate client code
    id("software.amazon.smithy.gradle.smithy-base")
}

// Add generated client source code to the main sourceSet
afterEvaluate {
    val clientPath = smithy.getPluginProjectionPath(smithy.sourceProjection.get(), "kotlin-codegen")
    sourceSets.main.get().kotlin.srcDir(clientPath)
}

tasks.named("compileKotlin") {
    dependsOn("smithyBuild")
}

dependencies {
    // Code generators
    compileOnly(libs.smithy.kotlin.aws.codegen)

    // Service model
    implementation(project(":smithy"))

    // Client Dependencies
    implementation(libs.smithy.kotlin.runtime.core)
    implementation(libs.smithy.kotlin.smithy.client)
    implementation(libs.smithy.kotlin.http.client)
    implementation(libs.smithy.kotlin.telemetry.api)
    implementation(libs.smithy.kotlin.telemetry.defaults)
    implementation(libs.smithy.kotlin.rpcv2.protocol)
    implementation(libs.smithy.kotlin.aws.protocol.core)
    implementation(libs.smithy.kotlin.aws.signing.common)
    implementation(libs.smithy.kotlin.serde)
    implementation(libs.smithy.kotlin.serde.cbor)
    implementation(libs.smithy.kotlin.http.client.engine.default)
    implementation(libs.kotlinx.coroutines.core)
}

val optinAnnotations = listOf("kotlin.RequiresOptIn", "aws.smithy.kotlin.runtime.InternalApi")
kotlin.sourceSets.all {
    optinAnnotations.forEach { languageSettings.optIn(it) }
}

application {
    mainClass.set("io.smithy.kotlin.client.example.MainKt")
}
