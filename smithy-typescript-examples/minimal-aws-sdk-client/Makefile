build:
	@echo Building the minimal sdk...
	yarn setup
	@echo Successfully built minimal sdk.

clean:
	@echo Cleaning...
	rm -rf build
	yarn clean
	@echo Cleaning Complete.

run: build
	@echo Running...
	yarn start
	@echo Finished running.

test-all: build
