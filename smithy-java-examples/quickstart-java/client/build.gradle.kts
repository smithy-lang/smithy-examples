description = "Cafe service client"

plugins {
    `java-library`
    // Executes smithy-build process to generate client code
    id("software.amazon.smithy.gradle.smithy-base")
}

dependencies {
    val smithyJavaVersion: String by project

    // === Code generators ===
    smithyBuild("software.amazon.smithy.java.codegen:plugins:$smithyJavaVersion")

    // === Service model ===
    implementation(project(":lib"))

    // === Client Plugins ===
    implementation(project(":plugins"))

    // === Client Dependencies ===
    // Core client dependency required by generated client code.
    implementation("software.amazon.smithy.java:client-core:$smithyJavaVersion")
    // Add client implementation of `RestJson1` protocol
    implementation("software.amazon.smithy.java:aws-client-restjson:$smithyJavaVersion")

    // Test dependencies
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

afterEvaluate {
    val clientPath = smithy.getPluginProjectionPath(smithy.sourceProjection.get(), "java-client-codegen")
    sourceSets {
        // Add generated client source code to the main sourceSet
        main {
            java {
                srcDir(clientPath)
            }
        }
        // Set up integration testing tasks source set
        create("it") {
            compileClasspath += main.get().output + configurations["testRuntimeClasspath"] + configurations["testCompileClasspath"]
            runtimeClasspath += output + compileClasspath + test.get().runtimeClasspath + test.get().output
        }
    }
}

tasks {
    // Ensure compilation happens after source-code generation
    val smithyBuild by getting
    compileJava {
        dependsOn(smithyBuild)
    }

    // This is set up integ tests separate from the `test` task to
    // avoid automatically running tests as part of build.
    val integ by registering(Test::class) {
        useJUnitPlatform()
        testClassesDirs = sourceSets["it"].output.classesDirs
        classpath = sourceSets["it"].runtimeClasspath
        // Allow the integ tests to print to stdout/stderr
        testLogging.showStandardStreams = true
    }
}
