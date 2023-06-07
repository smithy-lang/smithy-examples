
openapiReference = integ/expected-spec.openapi.json
openapiPath = openapi/build/smithy/source/openapi/Weather.openapi.json

clean:
	@echo Cleaning build Directories...
	rm -rf openapi/build
	@echo Cleaning Complete.

openapi: clean
	@echo Building openapi example...
	cd openapi; smithy build
	@echo openapi example built successfully

test-openapi: openapi
	@echo Comparing openapi build to reference...
	diff -b $(openapiReference) $(openapiPath)
	@echo openapi model matches reference

test-all: test-openapi
