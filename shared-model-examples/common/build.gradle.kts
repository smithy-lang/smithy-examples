
description = "A package used to share common shapes between smithy projects"

plugins {
    val smithyGradleVersion: String by project
    `java-library`
    id("software.amazon.smithy").version(smithyGradleVersion)
}

repositories {
    mavenLocal()
    mavenCentral()
}

buildscript {
    val smithyVersion: String by project

    // Set the version of the CLI for the smithy gradle plugin to use when building this project
    dependencies {
        classpath("software.amazon.smithy:smithy-cli:$smithyVersion")
        classpath("software.amazon.smithy:smithy-linters:$smithyVersion")
    }
}

// use the `package` projection to filter out validators so downstream consumers are not
// force to use them.
smithy {
    projection = "package"
    // Set tags so other smithy packages can include this in their built packages
    tags = setOf("common", "example")
}


dependencies {
    val smithyVersion: String by project
    implementation("software.amazon.smithy:smithy-linters:$smithyVersion")
}
