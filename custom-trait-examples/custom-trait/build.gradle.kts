description = "A package used to define custom traits"

plugins {
    id("com.github.spotbugs") version "6.4.8"
    id("software.amazon.smithy.gradle.smithy-trait-package")
}

dependencies {
    val smithyVersion: String by project
    // Pins the smithy-cli version
    smithyCli("software.amazon.smithy:smithy-cli:$smithyVersion")
    implementation("software.amazon.smithy:smithy-model:$smithyVersion")
}

repositories {
    mavenLocal()
    mavenCentral()
}
