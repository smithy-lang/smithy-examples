
description = "Custom Smithy structure trait with multiple inputs"

plugins {
    val smithyGradleVersion: String by project
    `java-library`
    id("com.github.spotbugs").version("4.7.1")
    id("software.amazon.smithy").version(smithyGradleVersion)
}

buildscript {
    val smithyVersion: String by project

    // Set the version of the CLI for the smithy gradle plugin to use when building this project
    dependencies {
        classpath("software.amazon.smithy:smithy-cli:$smithyVersion")
    }
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

/*
 * CheckStyle
 * ====================================================
 * https://docs.gradle.org/current/userguide/checkstyle_plugin.html
 */
apply(plugin = "checkstyle")
tasks["checkstyleTest"].enabled = false

// We don't need to lint tests.
tasks["spotbugsTest"].enabled = false

// Configure the bug filter for spotbugs.
spotbugs {
    setEffort("max")
    val excludeFile = File("${project.rootDir}/config/spotbugs/filter.xml")
    if (excludeFile.exists()) {
        excludeFilter.set(excludeFile)
    }
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    val smithyVersion: String by project

    implementation("software.amazon.smithy:smithy-model:$smithyVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.4.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.4.0")
}