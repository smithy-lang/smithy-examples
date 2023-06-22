description = "A package used to share a common linting configuration between smithy projects"

plugins {
    val smithyGradleVersion: String by project

    id("software.amazon.smithy").version(smithyGradleVersion)
}

buildscript {
    val smithyVersion: String by project

    // Set the version of the CLI for the smithy gradle plugin to use when building this project
    dependencies {
        classpath("software.amazon.smithy:smithy-cli:$smithyVersion")
    }
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    val smithyVersion: String by project

    implementation("software.amazon.smithy:smithy-linters:$smithyVersion")
}

smithy {
    tags = setOf("validators", "common-config")
}
