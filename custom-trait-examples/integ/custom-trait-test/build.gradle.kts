plugins {
    `java-library`
    id("software.amazon.smithy.gradle.smithy-base")
}

// The test project doesn't produce a JAR.
tasks["jar"].enabled = false

dependencies {
    val smithyVersion: String by project

    smithyCli("software.amazon.smithy:smithy-cli:$smithyVersion")
    implementation(project(":custom-trait-examples:custom-trait"))

    testImplementation("software.amazon.smithy:smithy-model:$smithyVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:6.0.1")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:6.0.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:6.0.1")
}

// Use Junit5's test runner.
tasks.withType<Test> {
    useJUnitPlatform()
}

smithy {
    smithyBuildConfigs.set(project.files())
}
