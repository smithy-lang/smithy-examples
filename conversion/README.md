# Smithy Conversions
This project demonstrates how to convert Smithy models to and from other formats
such as OpenAPI. 


### Examples
- [Smithy to OpenAPI](#smithy-to-openapi)


--- 

## Smithy to OpenAPI
This example demonstrates how the [`smithy-openapi`](https://search.maven.org/search?q=g:software.amazon.smithy%20and%20a:smithy-openapi)
plugin can be used to convert a Smithy model into an [OpenAPI](https://github.com/OAI/OpenAPI-Specification) specification.
The model used for this example is a slightly modified version of the Weather Service from the [Smithy quickstart guide](https://smithy.io/2.0/quickstart.html). 

The `smithy-openapi` [plugin](https://smithy.io/2.0/guides/building-models/build-config.html#plugins) is applied to the model 
by declaring the [smithy-openapi](https://search.maven.org/search?q=g:software.amazon.smithy%20and%20a:smithy-openapi) package as a dependency
in the `smithy-build.json` and adding the `openapi` plugin to the `openapi-conversion` [projection](https://smithy.io/2.0/guides/building-models/build-config.html#projections) 
in the `smithy-build.json` file. This will cause the `openapi` plugin to run when the `openapi-conversion` is built, generating an OpenAPI specification 
as a build artifact of the `openapi-conversion` projection.

*Note*: Smithy takes a different approach to modeling APIs from OpenAPI.
Smithy is protocol agnostic, which means it focuses on the interfaces and abstractions that are provided to end-users 
rather than how the data is sent over the wire. OpenAPI, on the other hand only defines RESTful APIs. Because these two
Because Smithy allows for a wider range of formats and a richer API model, conversions from Smithy to OpenAPI (and vice versa)
may be lossy.

For more information on converting Smithy models to OpenAPI, including additional configuration
options, see the guide [here](https://smithy.io/2.0/guides/converting-to-openapi.html).

### Use as a template
To use this example as a template run the following.

```
smithy init -t smithy-to-openapi -o <REPLACE_WITH_DESIRED_OUTPUT_DIRECTORY_NAME>
```

---