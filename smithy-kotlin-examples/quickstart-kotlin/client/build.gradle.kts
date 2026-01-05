description = "Cafe service client"

plugins {
    application
    kotlin("jvm") version "2.2.0"
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
    val coroutinesCoreVersion: String by project
    val smithyKotlinCodegenVersion: String by project
    val smithyKotlinRuntimeVersion: String by project

    // Code generators
    compileOnly("software.amazon.smithy.kotlin:smithy-aws-kotlin-codegen:$smithyKotlinCodegenVersion")

    // Service model
    implementation(project(":smithy"))

    // Client Dependencies
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesCoreVersion")
    implementation("aws.smithy.kotlin:runtime-core:$smithyKotlinRuntimeVersion")
    implementation("aws.smithy.kotlin:smithy-client:$smithyKotlinRuntimeVersion")
    implementation("aws.smithy.kotlin:http-client:$smithyKotlinRuntimeVersion")
    implementation("aws.smithy.kotlin:telemetry-api:$smithyKotlinRuntimeVersion")
    implementation("aws.smithy.kotlin:telemetry-defaults:$smithyKotlinRuntimeVersion")
    implementation("aws.smithy.kotlin:smithy-rpcv2-protocols:$smithyKotlinRuntimeVersion")
    implementation("aws.smithy.kotlin:aws-protocol-core:$smithyKotlinRuntimeVersion")
    implementation("aws.smithy.kotlin:aws-signing-common:$smithyKotlinRuntimeVersion")
    implementation("aws.smithy.kotlin:serde:$smithyKotlinRuntimeVersion")
    implementation("aws.smithy.kotlin:serde-cbor:$smithyKotlinRuntimeVersion")
    implementation("aws.smithy.kotlin:http-client-engine-default:$smithyKotlinRuntimeVersion")
}

val optinAnnotations = listOf("kotlin.RequiresOptIn", "aws.smithy.kotlin.runtime.InternalApi")
kotlin.sourceSets.all {
    optinAnnotations.forEach { languageSettings.optIn(it) }
}

application {
    mainClass.set("io.smithy.kotlin.client.example.MainKt")
}
