# Smithy Tutorial
This package serves as an introduction to a simple web application that uses a smithy-based client to make calls to a smithy-based server. This project is laid out in the following way for ease of learning:

### Model - `smithy/`
This directory contains the smithy model and smithy build configuration.

### Server - `server/`
This directory contains the server implementation, which is based on the generated server-sdk built from the smithy model.

### Client - `client/`
This directory is empty until the generated client is built from the smithy model. After a build, this will be populated with a code-generated client.

### Web application - `app/`
This directory contains the Next.js web-application that consumes the generated client and makes call to the server.

## Building
To build each of the packages, there are several distinct targets in the `Makefile`.

To build everything, run:
```
make build
```
This will build each distinct component of this project: the smithy model (`smithy/`), the client (`client/`), the application (`app/`), and the server (`server/`).

You may also independently build each component:
 * `make build-smithy`: build the smithy model and code-generate the client and server
 * `make build-ssdk`: set up and build the generated server-sdk (`ssdk`)
 * `make build-server`: build the server implementation
 * `make build-client`: set up and build the generated client
 * `make build-app`: build the web application

If you would like to clean up the project, run:
```
make clean
```
This will clean out all of the build artifacts for the entire project. You shouldn't need to do this unless you would like to have a completely clean slate.

## Running
### Server
To run the server in development mode, run:
```
make dev-server
```
The server will hot-reload on changes to the server code, making it easier to test small changes. The hot-reload behavior is useful for testing small changes to the server implementation, but cannot be used to test model changes without rebuilding the server-sdk (`make build-ssdk`) in this mode.

To run the server in production mode, run:
```
make run-server
```

Whether you launch in development mode or not, the server will run on `localhost:3001`. The web-application will make requests to this address.

### Web Application
To run the web-application in development mode, run:
```
make dev-app
```
The application will hot-load changes to the app, making it easier to test your changes. Any changes you make to the smithy model will require you to rebuild the client (`make build-client`) for them to reflect in this mode.

To run the app in production mode, run:
```
make run-app
```

Whether you launch in development mode or not, the web-application will run on `localhost:3000`. To access it, view this [address](http://localhost:3000) in your browser.
