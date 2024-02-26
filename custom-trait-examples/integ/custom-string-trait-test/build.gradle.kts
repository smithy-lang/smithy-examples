
plugins {
    `java-library`
    id("software.amazon.smithy.gradle.smithy-jar")
}


// The test project doesn't produce a JAR.
tasks["jar"].enabled = false


dependencies {
    val smithyVersion: String by project

    smithyCli("software.amazon.smithy:smithy-cli:$smithyVersion")

    implementation(project(":custom-trait-examples:custom-string-trait"))
}

smithy {
    smithyBuildConfigs.set(project.files())
}
