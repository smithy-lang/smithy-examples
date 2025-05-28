build-minimal-sdk: get-aws-models
	@echo Building the minimal sdk...
	yarn setup
	@echo Successfully built minimal sdk.

clean:
	@echo Cleaning build Directories...
	rm -rf build .models
	yarn clean
	@echo Cleaning Complete.

get-aws-models:
	@echo Retrieving models...
	mkdir .models
	cd .models && git clone git@github.com:aws/api-models-aws.git
	@echo Successfully retrieved models.

run: build-minimal-sdk
	@echo Running...
	yarn start
	@echo Finished running.

test-all: build-minimal-sdk
