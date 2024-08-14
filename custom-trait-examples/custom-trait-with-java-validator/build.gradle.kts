description = "A package used to define custom trait with a Java validator"

plugins {
    id("com.github.spotbugs").version("4.7.1")
    id("software.amazon.smithy.gradle.smithy-trait-package")
}

dependencies {
    val smithyVersion: String by project

    smithyCli("software.amazon.smithy:smithy-cli:$smithyVersion")
    implementation("software.amazon.smithy:smithy-model:$smithyVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.11.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.11.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

// Use Junit5's test runner.
tasks.withType<Test> {
    useJUnitPlatform()
}

repositories {
    mavenLocal()
    mavenCentral()
}


