
clean:
	@echo Cleaning build Directories...
	rm -rf build/ */build/ */dist */node_modules client/sdk server/ssdk app/.next
	@echo Cleaning Complete.

build-smithy:
	@echo Building smithy models...
	cd smithy; smithy build model/
	@echo Finished building models.

build-ssdk: build-smithy
	@echo Building server-sdk...
	cd server; ln -fs ../smithy/build/smithy/source/typescript-ssdk-codegen ssdk
	cd server/ssdk; yarn && yarn build
	@echo Finished building server-sdk.

build-server: build-ssdk
	@echo Building server...
	cd server; yarn && yarn build; yarn install
	@echo Finished building server.

build-client: build-smithy
	@echo Building client...
	cd client; ln -fs ../smithy/build/smithy/source/typescript-client-codegen sdk
	cd client/sdk; yarn && yarn build
	@echo Finished building client.

build-app: build-client
	@echo Building app...
	cd app; yarn && yarn build
	@echo Finished building app.

build: build-server build-client build-app

dev-server: build-ssdk
	cd server; yarn dev

dev-app: build-client
	cd app; yarn dev

run-server: build-server
	cd server; yarn start

run-app:
	cd app; yarn start
