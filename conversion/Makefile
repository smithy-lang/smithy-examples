
openapiReference = integ/expected-spec.openapi.json
openapiPath = smithy-to-openapi/build/smithy/source/openapi/Weather.openapi.json

clean:
	@echo Cleaning build Directories...
	rm -rf smithy-to-openapi/build
	@echo Cleaning Complete.

smithy-to-openapi: clean
	@echo Building openapi example...
	cd smithy-to-openapi; smithy build
	@echo openapi example built successfully

test-openapi: smithy-to-openapi
	@echo Comparing openapi build to reference...
	diff -b $(openapiReference) $(openapiPath)
	@echo openapi model matches reference

test-all: test-openapi
