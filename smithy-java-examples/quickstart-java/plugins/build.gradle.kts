description = "Defines plugins to configure generated clients"

plugins {
    `java-library`
}

dependencies {
    val smithyJavaVersion: String by project

    implementation("software.amazon.smithy.java:client-core:$smithyJavaVersion")
}
