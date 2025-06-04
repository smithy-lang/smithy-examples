## Minimal AWS SDK Client

This project provides a template to get started using [Smithy TypeScript](https://github.com/smithy-lang/smithy-typescript/) 
to create a minimal AWS SDK client (for DynamoDB).

For more information Smithy Typescript, see the [Smithy Typescript Quickstart Guide](https://smithy.io/2.0/typescript/quickstart.html).

### Layout 

- `smithy-build.json`: A Smithy configuration file that defines how and what to build.
- `package.json`: The node project configuration file. 
- `Makefile`: A makefile for quick usage of this project.
- `src/`: The code that uses the generated (minimal) client.

### Using as template
To use this example as a template run the following:

```console
smithy init -t smithy-typescript-minimal-aws-sdk
```

### Building and running the example

**NOTE**: This example assumes you have an existing AWS account, see [Additional Setup](#additional-setup) for preliminary instructions before running the example.

To build and run the example with one single command, run:
```console
make run
```
This command will build the minimal sdk from the smithy model, and then run the example code.

You can examine the generated code for the minimal (dynamodb) client under `sdk/`. You'll notice that under `src/commands`, there are only three commands generated for the operations we filtered! If you run `npm publish --dry-run` under `sdk/`, you can get an approximate size of our minimal client package. It's only ~40KB, which is approximately 1/5th the size of the full client (~200KB).

For cases where every byte counts, an AWS SDK client with only the operations you use can make a big difference. 

### Caveats

#### Endpoints
With how AWS code-generators are written, some generator logic is tied heavily to how endpoints are modelled by AWS services. This example strips this information from the model before the generator is ran, and therefore endpoint-related code is not generated. This means that the client must have the endpoint set explicitly, otherwise it will not function:
```typescript
    endpoint: {
        protocol: 'https',
        hostname: 'dynamodb.us-west-2.amazonaws.com',
        port: 443,
        path: '/'
    },
    region: 'us-west-2'
```

#### Incompatibilities
Given the above information, some AWS services require customizations by the AWS SDKs to correctly generate clients. Since we strip out endpoint information, those customizations may not function, and the client may not generate correctly. Therefore, your ability to generate a *working* minimal client for a given AWS service will vary.

#### Unsupported
Clients generated using the setup described here are not official SDK clients. Therefore, support for issues or problems encountered when using said generated clients will be limited. 
Please use at your own risk.

### Additional Setup

Using the `aws-cli`, execute the following command to create the table used in this example:

```console
aws dynamodb --region us-west-2 create-table \
  --table-name MyTestTable \
  --attribute-definitions AttributeName=Id,AttributeType=S \
  --key-schema AttributeName=Id,KeyType=HASH \
  --billing-mode PAY_PER_REQUEST
```

To clean up and remove the table, run:
```console
aws dynamodb --region us-west-2 delete-table --table-name MyTestTable
```
