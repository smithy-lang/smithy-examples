# Linting and Validation
The examples in this directory demonstrate how to create custom model validators and common validation configurations
for Smithy.

### Model Validators
[Model validators](https://smithy.io/2.0/spec/model-validation.html#validation) can be used by API designers and organizations 
to lint Smithy models and ensure that their APIs adhere to their own standards and best practices. Many validations can be defined directly within Smithy files. 
For examples, see the [Model Validation Examples](https://smithy.io/2.0/guides/model-validation-examples.html) guide.

### Linters
A linter is a special kind of [model validator](https://smithy.io/2.0/spec/model-validation.html#validation) that is configurable.
Linters are defined in code and can be configured in the `metadata` block of a Smithy file.

For example, we would configure the [`NonInclusiveTerms`](https://smithy.io/2.0/guides/model-linters.html?highlight=linter#noninclusiveterms) linter as follows:

```smithy
$version: "2"

metadata validators = [{
    name: "NoninclusiveTerms"
    configuration: {
        excludeDefaults: false,
        terms: {
            mankind: ["humankind"],
            mailman: ["mail carrier", "postal worker"]
        }
    }
}]
```

#### Validator/Linter Discovery
Custom model validator definitions are picked up in two ways. First, validators defined in the `metadata` section of the smithy model are picked up through the standard
smithy model discovery. The smithy CLI discovers models by searching JARs on the build classpath for smithy model definitions
within the `META-INF/smithy/` directory of the JAR.

Second, Java Validator definitions are discovered by a [Java service provider interface (SPI)](https://docs.oracle.com/javase/tutorial/sound/SPI-intro.html).

In order to be discoverable via SPI, a Java class defining a custom validator be a subclass of `software.amazon.smithy.model.validation.AbstractValidator`.
This validator class must then be added to a resource file `META-INF/services/software.amazon.smithy.model.validation.Validator` in order for the SPI to
discover the custom trait implementation. See the [custom validator](#custom-validator) example in this directory for an example implementation.

A Java class defining a custom linter should also be a subclass of `AbstractValidator`, but should define a nested provider class that is a subclass of `ValidatorService.Provider`.
The nested provider class must then be added to a resource file `META-INF/services/software.amazon.smithy.model.validation.ValidatorService` in order for the SPI to
discover the custom linter implementation. See the [custom linter](#custom-linter) example in this directory for an example implementation.

For more information on model validation see: [Model Validation](https://smithy.io/2.0/spec/model-validation.html#validation).


## Examples
- [Common Linting Configuration](#common-linting-configuration)
- [Custom Validator](#custom-validator)
- [Custom Linter ](#custom-linter)

---
## Common Linting Configuration
This example demonstrates how to create a package that defines a common set of [validators](https://smithy.io/2.0/spec/model-validation.html).
The `common-linting-configuration` package can be included as a dependency in other Smithy projects to enforce a common set
of standards across multiple projects. You could create one or more of these linting packages to enforce API standards
across your organization.

*Note*: the `common-linting-configuration` package depends on the `"software.amazon.smithy:smithy-linters"` package,
and you will need to include that as a dependency in addition to the `common-linting-configuration` package if you want
to use this example as a dependency in other projects.

### Use as a template
To use this example as a template run the following command.

```
smithy init -t common-linting-configuration
```

## Custom Validator
This example demonstrates how to create a custom [model validator](https://smithy.io/2.0/spec/model-validation.html#validation).

Unlike linters, the custom model validator defined by this example is not configurable and
will be used on your model when you add the custom-validator package as a dependency even if you
do not specify the custom validator in the `metadata validator` section of your Smithy file.


### Use as a template
To use this example as a template run the following command.

```
smithy init -t custom-validator
```

## Custom Linter
This example demonstrates how to create a custom [model linter](https://smithy.io/2.0/guides/model-linters.html).

The linter defined in this example allows you to forbid the use of a word within a
`@documentation` trait.

To use and configure the Linter defined by this example, include the generated package as a dependency in 
your project and add the following to the `metadata` section of one of your model files:

```
metadata validators = [
    {
        name: "ForbiddenDocumentation"
        severity: "WARNING"
        configuration: {
            forbid: ["meow"]
        }
    }
]
```

This will forbid the use of any `@documentation` traits within your model that also contain the word `meow`. 
For example, with the validator configured as above, the following model would throw a WARNING validation event: 

```
// This will fail the `ForbiddedDocumentation` validator
@documentation("meow is your time!")
string BadAbbreviationShapeID
```

For more information on writing custom linters see the guide [here](https://smithy.io/2.0/guides/model-linters.html?highlight=linter#writing-custom-linters).


### Use as a template
To use this example as a template run the following command.

```
smithy init -t custom-linter
```
