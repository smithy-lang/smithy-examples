description = "Smithy definition of a Cafe service."

plugins {
    `java-library`
    // Packages the models in this package into a jar for sharing/distribution by other packages
    id("software.amazon.smithy.gradle.smithy-jar")
}

dependencies {
    val smithyVersion: String by project

    // Adds the `@rpcv2Cbor` and `@restJson1` protocol trait
    api("software.amazon.smithy:smithy-aws-traits:$smithyVersion")

}

// Helps the Smithy IntelliJ plugin identify models
sourceSets {
    main {
        java {
            srcDir("model")
        }
    }
}
