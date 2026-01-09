description = "Smithy definition of a coffee shop service."

plugins {
    `java-library`
    // Packages the models in this package into a jar for sharing/distribution by other packages
    id("software.amazon.smithy.gradle.smithy-jar")
}

dependencies {
    // Adds the `@rpcv2Cbor` and `@restJson1` protocol traits
    api(libs.smithy.aws.traits)
}

// Helps the Smithy IntelliJ plugin identify models
sourceSets {
    main {
        java {
            srcDir("model")
        }
    }
}
