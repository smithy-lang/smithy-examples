
plugins {
    id("java-library")
    id("software.amazon.smithy.gradle.smithy-jar").version("0.8.0")
}


// The test project doesn't produce a JAR.
tasks["jar"].enabled = false

tasks.named<Test>("test") {
    useJUnitPlatform()
    systemProperty("buildFolder", project.buildDir.absolutePath)
    systemProperty("projectName", project.name)
    systemProperty("projectUnderTest",
        project(":linting-and-validation-examples:custom-validator").buildDir.absolutePath)
}
tasks["test"].dependsOn(tasks["smithyBuild"])

dependencies {
    val smithyVersion: String by project

    smithyCli("software.amazon.smithy:smithy-cli:$smithyVersion")

    implementation(project(":linting-and-validation-examples:custom-validator"))

    testImplementation("software.amazon.smithy:smithy-model:$smithyVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.4.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.4.0")
}
