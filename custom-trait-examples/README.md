# Custom Traits
The examples in this directory demonstrate how to create your own [traits](https://smithy.io/2.0/spec/model.html#traits)
in Smithy.

Traits are model components that can be attached to shapes to describe additional information about the shape;
shapes provide the structure and layout of an API, while traits provide refinement and style.

### Custom trait packages
A custom trait package can be created to distribute your trait definition for use in both Smithy models and in code generators.

Custom trait definition typically consist of both: 
- A Java class that provides trait definitions usable by Java code generators 
- A Smithy model providing the definition of the trait as a Smithy shape

The Java definition provides a way for code generators to interact with the data contained in the trait while the 
Smithy model defines the model structure and constraints such as [breaking change rules](https://smithy.io/2.0/spec/model.html#breaking-change-rules).

#### Custom Trait Discovery
Custom trait definitions are picked up in two ways. First, smithy model definitions are picked up through the standard 
smithy model discovery. The smithy CLI discovers models by searching JARs on the build classpath for smithy model definitions 
within `META-INF/smithy/` directory of the JAR.

Second, Java trait definitions are discovered by a [Java service provider interface (SPI)](https://docs.oracle.com/javase/tutorial/sound/SPI-intro.html). 
In order to be discoverable via SPI, the Java class defining the trait be a subclass of `AbstractTrait` and should define
a nested `Provider` class that implements `software.amazon.smithy.model.traits.TraitService`. That provider class must 
then be added to a resource file `META-INF/services/software.amazon.smithy.model.traits.TraitService` in order for the SPI to 
discover the custom trait implementation. See the examples in this directory for example implementations.

For more information on defining traits see the [Smithy specification](https://smithy.io/2.0/spec/model.html#defining-traits)


## Examples
- [Custom trait](#custom-trait)
- [Custom trait with Java validator](#custom-trait-with-java-validator)
- [Custom, handwritten trait](#custom-handwritten-trait)

--- 
## Custom Trait
This example demonstrates how to create a package for custom traits using the `smithy-trait-package` Gradle plugin 
and Smithy's `trait-codegen` build plugin.

### Use as a template
To use this example as a template run the following command.

```console
smithy init -t custom-trait
```

## Custom trait with Java validator
This example demonstrates how to create a package for custom traits that includes a custom Java validator. This example
uses the `smithy-trait-package` Gradle plugin and Smithy's `trait-codegen` build plugin to configure the package and generate traits.

Many custom traits require additional validations to ensure they are used correctly. Customers can use 
[trait validators](https://smithy.io/2.0/spec/model-validation.html#smithy-api-traitvalidators-trait) 
to apply most validation. However, sometimes advanced validation that cannot be easily expressed with Smithy selectors 
is required. [Custom Java validators](https://smithy.io/2.0/guides/model-linters.html#writing-custom-validators) can 
be used apply advanced model validation using the Java programming language.

### Use as a template
To use this example as a template run the following command.

```console
smithy init -t custom-trait-with-java-validator
```

## Custom, handwritten trait
Trait codegen can be used in most cases when defining a custom trait, but some customers may wish to customize the Java 
definitions of their traits to support additional behavior such as parsing a string into a URL. To support such use cases
customer can opt to handwrite their trait definitions. Such handwritten trait definitions can be included in a package alongside 
automatically generated Java trait definitions.

This package demonstrates how to include a handwritten Java trait alongside traits generated by the `trait-codegen`
plugin.

### Use as a template
To use this example as a template run the following command.

```console
smithy init -t custom-trait-handwritten
```
