
rootProject.name = "common-shapes"

pluginManagement {
    val smithyGradleVersion: String by settings

    plugins {
        id("software.amazon.smithy.gradle.smithy-jar").version(smithyGradleVersion)
    }

    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
}

