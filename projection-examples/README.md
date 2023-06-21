# Projections
[Projections](https://smithy.io/2.0/guides/building-models/build-config.html#projections)
are filtered and modified versions of a Smithy model intended for a specific audience or customer. 


## Examples 
- [Filter out internal data](#filter-out-internal-data)

--- 

## Filter out internal data
This example demonstrates how to use projections to filter internal data out of your model
so you have a modified version of the model that is safe for external consumers to view.

The `external` projection used in this example is defined in the `smithy-build.json` file and filters out shapes marked with the
[internal](https://smithy.io/2.0/spec/documentation-traits.html#smithy-api-internal-trait) trait or with the internal [tag](https://smithy.io/2.0/spec/documentation-traits.html#smithy-api-tags-trait).
It also filters out any shapes marked with `beta` tag. This allows you to filter out internal traits and routes from
a version of your model intended for consumption by external customers. For example, you might use the model generated
by the `external` projection to generate documentation for external customers.

This example also demonstrates how you can only include specific metadata fields in an external projection via the
`includeMetadata` transform.

The model used in this example is the weather service from the [quickstart guide](https://smithy.io/2.0/quickstart.html).

### Using as template
To use this example as a template run the following.

```console
smithy init -t filter-internal-projection
```