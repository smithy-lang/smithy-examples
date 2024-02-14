description = "A package used to share a common linting configuration between smithy projects"

plugins {
    `java-library`
    id("software.amazon.smithy.gradle.smithy-jar")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    val smithyVersion: String by project

    smithyCli("software.amazon.smithy:smithy-cli:$smithyVersion")
    implementation("software.amazon.smithy:smithy-linters:$smithyVersion")
}

smithy {
    tags.addAll("validators", "common-config")
}
