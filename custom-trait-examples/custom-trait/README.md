# Custom trait
Use this package to create a customizable [trait](https://smithy.io/2.0/spec/model.html#traits) package. This 
package will include both a trait definitions in the Smithy IDL and generated Java definitions for those traits. 

## Building
To build this package run the following from the root of the package:

```console
./gradlew clean build
```

This will generate a `build` directory containing the build artifacts generated by
gradle. The generated JAR file that should be used by downstream consumers can be
found at `build/libs/custom-trait.jar`


## Customization
To customize this package, start by updating the `settings.gradle.kts` file and update
the `rootProject.name` property to the desired name of your package. Also update the 
`description` at the top of the `build.gradle.kts` file.

Next, update the `model/custom-trait.smithy` file to update the trait definition with your
new trait. For more information on defining traits in a Smithy model see: [Defining traits](https://smithy.io/2.0/spec/model.html?highlight=annotation#defining-traits). 

Traits defined in this package will have Java definitions generated by the [`trait-codegen`](https://github.com/smithy-lang/smithy/tree/main/smithy-trait-codegen) Smithy build plugin. 
Generated Java trait definitions are automatically included in the `main` sourceSet and compiled alongside any other 
Java code in the project.

Once you have defined your trait in the Smithy model you can re-build the project to validate your new trait definitions and 
re-generate any Java definitions of those traits.

## Distribution
If you want to distribute the JAR file create by this package, consider adding the
[maven-publish](https://docs.gradle.org/current/userguide/publishing_maven.html) plugin to the project to publish the JAR to a maven repository.