
plugins {
    val smithyGradleVersion: String by project

    id("software.amazon.smithy").version(smithyGradleVersion)
}


// The test project doesn't produce a JAR.
tasks["jar"].enabled = false

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
    implementation(project(":custom-trait-examples:custom-structure-trait"))
}
