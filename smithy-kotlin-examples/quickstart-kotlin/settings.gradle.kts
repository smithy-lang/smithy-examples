rootProject.name = "smithy-kotlin-quickstart"

pluginManagement {
    val smithyGradleVersion = "1.3.0"
    plugins {
        id("software.amazon.smithy.gradle.smithy-jar").version(smithyGradleVersion)
        id("software.amazon.smithy.gradle.smithy-base").version(smithyGradleVersion)
    }

    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
}

// Subprojects
include("client")
include("smithy")
include("server")
