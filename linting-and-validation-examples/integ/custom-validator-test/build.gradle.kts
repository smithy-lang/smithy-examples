
plugins {
    val smithyGradleVersion: String by project

    id("software.amazon.smithy").version(smithyGradleVersion)
}


// The test project doesn't produce a JAR.
tasks["jar"].enabled = false
tasks["smithyBuildJar"].enabled = false

tasks.create<software.amazon.smithy.gradle.tasks.SmithyBuild>("buildPackage") {
    addRuntimeClasspath = true
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    systemProperty("buildFolder", project.buildDir.absolutePath)
    systemProperty("projectName", project.name)
    systemProperty("projectUnderTest",
        project(":linting-and-validation-examples:custom-validator").buildDir.absolutePath)
}
tasks["test"].dependsOn(tasks["buildPackage"])


repositories {
    mavenLocal()
    mavenCentral()
}

buildscript {
    val smithyVersion: String by project

    // Set the version of the CLI for the smithy gradle plugin to use when building this project
    dependencies {
        classpath("software.amazon.smithy:smithy-cli:$smithyVersion")
    }
}

dependencies {
    val smithyVersion: String by project

    implementation(project(":linting-and-validation-examples:custom-validator"))

    testImplementation("software.amazon.smithy:smithy-model:$smithyVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.4.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.4.0")
}
