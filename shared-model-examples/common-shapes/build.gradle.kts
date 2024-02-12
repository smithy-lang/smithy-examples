
description = "A package used to share common shapes between smithy projects"

plugins {
    `java-library`
    id("software.amazon.smithy.gradle.smithy-jar").version("0.10.0")
}

repositories {
    mavenLocal()
    mavenCentral()
}

// use the `package` projection to filter out validators so downstream consumers are not
// force to use them.
smithy {
    sourceProjection.set("package")
    // Set tags so other smithy packages can include this in their built packages
    tags.addAll("common", "example")
}


dependencies {
    val smithyVersion: String by project

    smithyCli("software.amazon.smithy:smithy-cli:$smithyVersion")
    implementation("software.amazon.smithy:smithy-linters:$smithyVersion")
}
