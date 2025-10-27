plugins {
    java
    // Executes smithy-build process to generate server stubs
    id("software.amazon.smithy.gradle.smithy-base")
}

description = "Cafe service server implementation"

dependencies {
    val smithyRsVersion: String by project

    // === Code generators ===
    smithyBuild("software.amazon.smithy.rust:codegen-server:$smithyRsVersion")

    // === Service model ===
    implementation(project(":smithy"))

}

