description = "A package used to define an annotation trait"

plugins {
    `java-library`
    id("com.github.spotbugs").version("4.7.1")
    id("software.amazon.smithy.gradle.smithy-jar").version("0.8.0")
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

/*
 * Spotbugs
 * ====================================================
 */
apply(plugin = "com.github.spotbugs")

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

    smithyCli("software.amazon.smithy:smithy-cli:$smithyVersion")

    implementation("software.amazon.smithy:smithy-model:$smithyVersion")
}
