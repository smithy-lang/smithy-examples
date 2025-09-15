## Smithy-Rust Quickstart

This project provides a template to get started using [Smithy Rust](https://github.com/smithy-lang/smithy-rs/) 
to create Rust clients and servers.

For more information on this example, see the [Smithy Rust Quickstart Guide](https://smithy.io/2.0/rust/quickstart.html).

### Layout 

- `client/`: Code generated client that can call the server.
- `smithy/`: Common package for the service API model. Shared by both client and server.
- `server/`: Code generated Server that implements stubbed operations code-generated from the service model.

### Usage

To create a new project from this template, use the [Smithy CLI](https://smithy.io/2.0/guides/smithy-cli/index.html)
`init` command as follows:

```console
smithy init -t smithy-rust-quickstart
```

### Running and testing server

First generate the client and server SDKs

```console
./gradlew build
```

To run and test the server:

```console
cargo run --bin server
```

That will start the server running on port `8888`.

Once the server is running you can call the server using `curl`

```console
curl -H "content-type: application/json" -d '{"coffeeType": "LATTE"}' -X POST localhost:8888/order
```

or by running the client application in the `client` subproject


```console
cargo run --bin client
```
