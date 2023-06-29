
referenceModel = integ/expected-model.json
cliModelPath = quickstart-cli/build/smithy/source/model/model.json
gradleModelPath = quickstart-gradle/build/smithyprojections/quickstart-gradle/source/model/model.json

clean:
	@echo Cleaning build Directories...
	rm -rf quickstart-cli/build
	cd quickstart-gradle; ./gradlew clean
	@echo Cleaning Complete.

cli-example: clean
	@echo Building quickstart-cli example...
	cd quickstart-cli; smithy build
	@echo quickstart-cli example built successfully

test-cli: cli-example
	@echo Comparing cli build to reference...
	diff -b $(referenceModel) $(cliModelPath)
	@echo Cli model matches reference

gradle-example: clean
	@echo Building quickstart-gradle example...
	cd quickstart-gradle; ./gradlew build
	@echo quickstart-gradle example built successfully

test-gradle: gradle-example
	@echo Comparing gradle build to reference...
	diff -b $(referenceModel) $(gradleModelPath)
	@echo Gradle model matches reference

test-all: test-cli test-gradle
