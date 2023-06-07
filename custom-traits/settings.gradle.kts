
pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "custom-trait-example"

// Template directories
include(":custom-annotation-trait")
include(":custom-string-trait")
include(":custom-structure-trait")

// Integration Tests
include(":integ:custom-annotation-trait-test")
include(":integ:custom-string-trait-test")
include(":integ:custom-structure-trait-test")