
filterOutInternalReference = integ/expected-filter-out-internal.json
filterOutInternalPath = filter-out-internal/build/smithy/external/model/model.json

clean:
	@echo Cleaning build Directories...
	rm -rf filter-out-internal/build
	@echo Cleaning Complete.

filter-out-internal: clean
	@echo Building filter-out-internal example...
	cd filter-out-internal; smithy build
	@echo filter-out-internal example built successfully

test-filter-out-internal: filter-out-internal
	@echo Comparing filter-out-internal build to reference...
	diff -b $(filterOutInternalReference) $(filterOutInternalPath)
	@echo filter-out-internal model matches reference

test-all: test-filter-out-internal
