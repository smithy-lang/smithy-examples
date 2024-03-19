
plugins {
    `java-library`
    id("software.amazon.smithy.gradle.smithy-jar").version("0.10.1")
}

repositories {
    mavenCentral()
}

dependencies {
    // Use common models
    implementation(project(":lib"))
}

