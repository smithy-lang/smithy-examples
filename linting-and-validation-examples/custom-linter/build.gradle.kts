import com.github.spotbugs.snom.Effort

description = "A custom Smithy model-linter"

plugins {
    `java-library`
    id("com.github.spotbugs") version "6.4.4"
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
    effort = Effort.MAX
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

    testImplementation("org.junit.jupiter:junit-jupiter-api:6.0.1")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:6.0.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:6.0.1")
}
