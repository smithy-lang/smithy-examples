# Shared Model Package
This example demonstrates how to create a package that defines a common set of models that can be shared between 
Smithy packages. You could create one or more of these commons shapes packages to create standardized definitions 
for your organization and reduce duplication across Smithy models. 


## Using Projections to filter out data for downstream consumers
The `common-shapes` package uses the `package` projection instead of the `source` projection.
[Projections](https://smithy.io/2.0/guides/building-models/build-config.html#projections)
are filtered and modified versions of a Smithy model intended for a specific audience or customer. The `package`
projection used in this example is defined in the `smithy-build.json` file and filters validators out of the resulting
model. This allows us to enforce validations for the `common-shapes` package without enforcing these validations on
downstream consumers of the `common-shapes` package.

## Using as Template
To use this example as a template run the following.

```
smithy init -t shared-shapes
```

