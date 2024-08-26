.DEFAULT_GOAL:=help

help:  ## Display this help
	@awk 'BEGIN {FS = ":.*##"; printf "\nUsage:\n  make \033[36m<target>\033[0m\n\nTargets:\n"} /^[a-zA-Z_-]+:.*?##/ { printf "  \033[36m%-13s\033[0m %s\n", $$1, $$2 }' $(MAKEFILE_LIST)

init: ## Initialize the project and set it to the tutorial state
	@echo Initializing...
	git init; git add -A; git commit -m 'Initial commit'; git apply .patches/start.patch
	@echo Initialized.

clean: ## Clean all build artifacts of this project
	@echo Cleaning build Directories...
	rm -rf build/ */build/ */dist */node_modules client/sdk server/ssdk app/.next
	@echo Cleaning Complete.

build-smithy: ## Build the smithy model and code-generate the client and server
	@echo Building smithy models...
	cd smithy; smithy format model/; smithy build model/
	@echo Finished building models.

build-ssdk: build-smithy ## Set up and build the generated server-sdk (`ssdk`)
	@echo Building server-sdk...
	cd server; ln -fs ../smithy/build/smithy/source/typescript-ssdk-codegen ssdk
	cd server/ssdk; yarn && yarn build
	@echo Finished building server-sdk.

build-server: build-ssdk ## Build the server implementation
	@echo Building server...
	cd server; yarn && yarn build; yarn install
	@echo Finished building server.

build-client: build-smithy ## Set up and build the generated client
	@echo Building client...
	cd client; ln -fs ../smithy/build/smithy/source/typescript-client-codegen sdk
	cd client/sdk; yarn && yarn build
	@echo Finished building client.

build-app: build-client ## Build the web application
	@echo Building app...
	cd app; yarn && yarn build
	@echo Finished building app.

build: build-server build-client build-app ## Build the entire project

dev-server: build-ssdk ## Run the server in development-mode
	cd server; yarn && yarn dev

dev-app: build-client ## Run the web application in development-mode
	cd app; yarn && yarn dev

dev: # Run the server and web application in development-mode in the same session
	$(MAKE) -j 2 dev-server dev-app

run-server: build-server ## Run the server
	cd server; yarn start

run-app: build-app ## Run the web application
	cd app; yarn start

run: ## Run the server and web application in the same session
	$(MAKE) -j 2 run-server run-app

test-all: build
