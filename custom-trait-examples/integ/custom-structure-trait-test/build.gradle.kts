
plugins {
    id("java-library")
    id("software.amazon.smithy.gradle.smithy-jar").version("0.10.0")
}

// The test project doesn't produce a JAR.
tasks["jar"].enabled = false

dependencies {
    val smithyVersion: String by project

    smithyCli("software.amazon.smithy:smithy-cli:$smithyVersion")

    implementation(project(":custom-trait-examples:custom-structure-trait"))
}
