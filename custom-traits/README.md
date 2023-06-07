# Custom Traits
The examples in this directory demonstrate how to create a custom trait package for Smithy. 

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
- [Custom Annotation Trait](#custom-annotation-trait)
- [Custom String Trait](#custom-string-trait)
- [Custom Structure Trait](#custom-structure-trait)

--- 
## Custom Annotation Trait
This example demonstrates how to create a custom [Annotation trait](https://smithy.io/2.0/spec/model.html?highlight=annotation#annotation-traits)
package. Annotation traits are structure traits with no members.

For example, you might use an annotation trait `smithy.example#beta` to mark an operation as still in `beta`:
```
use smithy.example#beta 

@beta
operation MyOperation {
  input: MyOperationInput
  output: MyOperationOutput
}

```

Annotation traits can be defined as Smithy shapes without code. For example:
```
$version: "2"
namespace smithy.example

@trait
structure foo {}
```

However, sometimes defining the trait as Java class in addition to a smithy model makes it easier for the trait to be used by
other Java code such as code generators.

### Use as a template
To use this example as a template run the following command.

```
smithy init -t custom-annotation-trait
```

## Custom String Trait
This example demonstrates how to create a custom String trait package. String traits are string shapes with the `@trait`
trait applied, and they accept a single string argument.

For example, you might use a string trait `smithy.example#label` to add a label to a structure:
```
use smithy.example#label

@label("myLabel")
structure MyStructure {
  value: String
}
```
Many custom traits require additional validations to ensure they are used correctly. This example also adds a validator 
for the string trait that performs additional checks on the trait's usage.

*Note*: the validator defined in this package will be used by packages that declare this custom trait package as a
dependency without being added to the `metadata validators` metadata key.

### Use as a template
To use this example as a template run the following command.

```
smithy init -t custom-string-trait
```

## Custom Structure Trait
This example demonstrates how to create a custom complex trait package. This complex trait has multiple inputs.

Complex structure traits can be useful for adding complex metadata to a shape for applications
such as code generation.

For example, the [`smithy.api#http`](https://smithy.io/2.0/spec/http-bindings.html#smithy-api-http-trait) trait defines multiple
inputs that are used by client and server code generators for generating http route handlers.

```smithy
@idempotent
@http(method: "PUT", uri: "/{bucketName}/{key}", code: 200)
operation PutObject {
    input: PutObjectInput
}
```
Many custom traits require these additional validations to ensure they are used correctly. This example also adds a validator 
for the custom trait that performs additional checks on the usage of the trait. 

*Note*: the validator defined in this package will be used by packages that declare this custom trait package as a 
dependency without being added to the `metadata validators` metadata key.

### Use as a template
To use this example as a template run the following command.

```
smithy init -t custom-structure-trait
```
