rootProject.name = "linting-and-validation-example"

pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
}

// templates
include(":custom-linter")
include(":common-linting-configuration")
include(":custom-validator")

// integration tests
include(":integ:common-linting-configuration-test")
include(":integ:customer-linter-test")
include(":integ:customer-validator-test")