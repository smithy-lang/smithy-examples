## Smithy-Kotlin Quickstart

This project provides a template to get started using [Smithy Kotlin](https://github.com/smithy-lang/smithy-kotlin)
to create Kotlin clients.

### Layout

- `client/`: Code generated Kotlin client that can call the server.
- `smithy/`: Common package for the service API model. Shared by both client and server.
- `server/`: Code generated Java Server that implements stubbed operations code-generated from the service model.

### Usage

To create a new project from this template, use the [Smithy CLI](https://smithy.io/2.0/guides/smithy-cli/index.html)
`init` command as follows:

```console
smithy init -t smithy-kotlin-quickstart
```

### Running and testing client

To run and test the client, first start a server by running `./gradlew :server:run` from the root of a project created from this
template. That will start the server running on port `8888`.

Once the server is running you can run the client application in the `client` subproject

```console
./gradlew :client:run
```
