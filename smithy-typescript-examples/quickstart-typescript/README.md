## Smithy-Typescript Quickstart

This project provides a template to get started using [Smithy TypeScript](https://github.com/smithy-lang/smithy-typescript/) 
to create Typescript clients and servers.

For more information on this example, see the [Smithy Typescript Quickstart Guide](https://smithy.io/2.0/typescript/quickstart.html).

### Layout 

- `client/`: Code generated client that can call the server.
- `smithy/`: Common package for the service API model. Shared by both client and server.
- `server/`: Code generated Server that implements stubbed operations code-generated from the service model.

### Usage

To create a new project from this template, use the [Smithy CLI](https://smithy.io/2.0/guides/smithy-cli/index.html)
`init` command as follows:

```console
smithy init -t smithy-typescript-quickstart
```

### Running and testing server

To run and test the server, navigate to the `server` directory and run the following:
```console
yarn setup && yarn start
```
This command will start the server on port `8888`.

Once the server is running, you may call the server using `curl`:

```console
curl -H "content-type: application/json" -d '{"coffeeType": "LATTE"}' -X POST localhost:8888/order
```

or, by running the client application in the `client` directory:

```console
yarn setup && yarn start
```
