
pluginManagement {
    val smithyGradleVersion: String by settings
    plugins {
        id("software.amazon.smithy.gradle.smithy-jar").version(smithyGradleVersion)
        id("software.amazon.smithy.gradle.smithy-base").version(smithyGradleVersion)
        id("software.amazon.smithy.gradle.smithy-trait-package").version(smithyGradleVersion)
    }

    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "smithy-examples"

// ---- custom-trait-examples ----
// Template directories
include(":custom-trait-examples:custom-trait")
include(":custom-trait-examples:custom-trait-with-java-validator")
include(":custom-trait-examples:custom-trait-handwritten")

// Integration Tests
include(":custom-trait-examples:integ:custom-trait-test")
include(":custom-trait-examples:integ:custom-trait-with-java-validator-test")
include(":custom-trait-examples:integ:custom-trait-handwritten-test")

// ---- shared models ----
// Template directories
include(":shared-model-examples:common-shapes")

// Integration Tests
include(":shared-model-examples:integ")

// ---- linting and validation ----
// templates
include(":linting-and-validation-examples:custom-linter")
include(":linting-and-validation-examples:common-linting-configuration")
include(":linting-and-validation-examples:custom-validator")
include(":linting-and-validation-examples:decorators")

// integration tests
include(":linting-and-validation-examples:integ:common-linting-configuration-test")
include(":linting-and-validation-examples:integ:custom-linter-test")
include(":linting-and-validation-examples:integ:custom-validator-test")
include(":linting-and-validation-examples:integ:decorators-test")

// ---- Gradle Plugin Examples -----
// Templates
includeBuild("gradle-plugin-examples/tutorial")

// Integ test
include(":gradle-plugin-examples:integ:tutorial")

// ---- Smithy-Java examples ----
// templates
includeBuild("smithy-java-examples/quickstart-java")

// integration tests
include(":smithy-java-examples:integ")

// ---- Smithy-Rust examples ----
// templates
includeBuild("smithy-rs-examples/quickstart-rust")
