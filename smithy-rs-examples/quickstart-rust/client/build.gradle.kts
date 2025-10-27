plugins {
    java
    // Executes smithy-build process to generate client code
    id("software.amazon.smithy.gradle.smithy-base")
}

description = "Cafe service client"

dependencies {
    val smithyRsVersion: String by project

    // === Code generators ===
    smithyBuild("software.amazon.smithy.rust:codegen-client:$smithyRsVersion")

    // === Service model ===
    implementation(project(":smithy"))

}

tasks.jar.configure {
    enabled = false
}

