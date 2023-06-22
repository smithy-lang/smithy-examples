
pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "smithy-examples"

// ---- custom-trait-examples ----
// Template directories
include(":custom-trait-examples:custom-annotation-trait")
include(":custom-trait-examples:custom-string-trait")
include(":custom-trait-examples:custom-structure-trait")

// Integration Tests
include(":custom-trait-examples:integ:custom-annotation-trait-test")
include(":custom-trait-examples:integ:custom-string-trait-test")
include(":custom-trait-examples:integ:custom-structure-trait-test")

// ---- shared models ----
// Template directories
include(":shared-model-examples:common")

// Integration Tests
include(":shared-model-examples:integ")