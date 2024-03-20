### Smithy Gradle Plugin Tutorial 

An example Smithy project that demonstrates how to use [Smithy Gradle plugins](https://github.com/smithy-lang/smithy-gradle-plugin)
by creating a simple Cafe service model.

This project: 
1. Sets up a `lib` project containing Smithy models that are shared across other Smithy projects.
2. Uses these shared models as a dependency of the `service` package to create a Cafe service model. 
3. Create a custom linter in the `lib` package that can be used by consumers of the `lib` package to
   check for `externalDocumentation` links that do not match an expected site address.

For more information on the use of the Smithy Gradle plugins see the [plugin guide](https://smithy.io/2.0/guides/gradle-plugin/index.html).

## Building
To build this package run the following from the root of the package:

```
./gradlew clean build
```

This will generate a `build` directory in both the `lib` and `service` subprojects 
containing the build artifacts generated by gradle.

## Distribution
If you want to distribute either of the JAR files created by this package, consider adding the
[maven-publish](https://docs.gradle.org/current/userguide/publishing_maven.html) plugin to the project to publish the JAR to a maven repository.