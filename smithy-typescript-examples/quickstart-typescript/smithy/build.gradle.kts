description = "Smithy definition of a Cafe service."

plugins {
    `java-library`
    // Packages the models in this package into a jar for sharing/distribution by other packages
    id("software.amazon.smithy.gradle.smithy-jar")
}

dependencies {
    val smithyVersion: String by project
    implementation("software.amazon.smithy:smithy-model:$smithyVersion")
    implementation("software.amazon.smithy:smithy-aws-traits:$smithyVersion")
    implementation("software.amazon.smithy:smithy-validation-model:$smithyVersion")
    implementation("software.amazon.smithy.typescript:smithy-aws-typescript-codegen:0.22.0")
}

// Helps the Smithy IntelliJ plugin identify models
sourceSets {
    main {
        java {
            srcDir("model")
        }
    }
}
