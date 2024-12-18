
plugins {
    id("java-library")
}

// The test project doesn't produce a JAR.
tasks["jar"].enabled = false

tasks.named<Test>("test") {
    useJUnitPlatform()
    systemProperty("buildFolder",
        gradle.includedBuild("tutorial").projectDir.resolve("service").resolve("build").absolutePath)
}

tasks["test"].dependsOn(gradle.includedBuild("tutorial").task(":service:build"))

dependencies {
    val smithyVersion: String by project

    testImplementation("software.amazon.smithy:smithy-model:$smithyVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.4")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.11.4")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.11.4")
    testImplementation("org.hamcrest:hamcrest:3.0")
}
