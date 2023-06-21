# Getting started Example
This directory provides examples of building complete Smithy model for 
the Weather Service from the [Smithy Quick Start Guide](https://smithy.io/2.0/quickstart.html) 
using a number of different build systems.

## Build Systems
- [Smithy CLI](#smithy-cli)
- [Gradle](#gradle)
--- 

## Smithy CLI
This example uses the [smithy cli](https://smithy.io/2.0/guides/smithy-cli/index.html) to build the Weather Service model.

### Usage
You can use this example in your local workspace by executing the following command:
```
smithy init
```
Then, build the example by executing `smithy build` in the newly created `getting-started`
directory.


## Gradle
This examples use the [smithy-gradle-plugin](https://github.com/awslabs/smithy-gradle-plugin) to build the Weather Service model.

```
smithy init -t getting-started-gradle
```

To build the Weather Service model run `./gradlew clean build` from the newly created `getting-started-gradle` directory.