plugins {
    `java-library`
    id("software.amazon.smithy.gradle.smithy-base")
}

// The test project doesn't produce a JAR.
tasks["jar"].enabled = false

dependencies {
    val smithyVersion: String by project

    smithyCli("software.amazon.smithy:smithy-cli:$smithyVersion")
    implementation(project(":custom-trait-examples:custom-trait-with-java-validator"))

    testImplementation("software.amazon.smithy:smithy-model:$smithyVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.3")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.3")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.3")
}

// Use Junit5's test runner.
tasks.withType<Test> {
    useJUnitPlatform()
}

smithy {
    smithyBuildConfigs.set(project.files())
}
