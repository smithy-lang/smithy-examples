## Smithy-Java Quickstart

This project provides a template to get started using [Smithy Java](https://github.com/smithy-lang/smithy-java/) 
to create Java clients and servers.

For more information on this example see the [Smithy Java Quickstart Guide](https://smithy.io/2.0/java/quickstart.html).

### Layout 
- `lib/`: Common package for the service API model. Shared by both client and server.
- `server/`: Code generated Server that implements stubbed operations code-generated from the service model.
- `client/`: Code generated client that can call the server.
- `plugins/`: a package defining client plugins.

### Usage

Then, to create a new project from this template, use the [Smithy CLI](https://smithy.io/2.0/guides/smithy-cli/index.html)
`init` command as follows:

```console
smithy init -t smithy-java-quickstart
```

### Running and testing server

To run and test the server, run `./gradlew run` from the root of a project created from this
template. That will start the server running on port `8888`.

Once the server is running you can call the server using curl or by executing the integration tests in `client` subproject:  
- `curl`: `curl -H "content-type: application/json" -d '{"coffeeType": "LATTE"}' -X POST localhost:8888/order`
- integration tests: `./gradlew :client:integ`
