plugins {
    id("java-library")
    id("software.amazon.smithy.gradle.smithy-jar").version("0.8.0")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    val smithyVersion: String by project

    smithyCli("software.amazon.smithy:smithy-cli:$smithyVersion")

    implementation(project(":example-client-integrations"))
    implementation("software.amazon.smithy.typescript:smithy-typescript-codegen:0.19.0")

    // Uncomment below to add various smithy dependencies (see full list of smithy dependencies in https://github.com/awslabs/smithy)
    // implementation("software.amazon.smithy:smithy-model:$smithyVersion")
    // implementation("software.amazon.smithy:smithy-linters:$smithyVersion")
}
