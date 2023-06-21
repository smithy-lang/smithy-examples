
referenceModel = integ/expected-model.json
cliModelPath = getting-started-cli/build/smithy/source/model/model.json
gradleModelPath = getting-started-gradle/build/smithyprojections/getting-started-gradle/source/model/model.json

clean:
	@echo Cleaning build Directories...
	rm -rf getting-started-cli/build
	cd getting-started-gradle; ./gradlew clean
	@echo Cleaning Complete.

cli-example: clean
	@echo Building getting-started-cli example...
	cd getting-started-cli; smithy build
	@echo getting-started-cli example built successfully

test-cli: cli-example
	@echo Comparing cli build to reference...
	diff -b $(referenceModel) $(cliModelPath)
	@echo Cli model matches reference

gradle-example: clean
	@echo Building getting-started-gradle example...
	cd getting-started-gradle; ./gradlew build
	@echo getting-started-gradle example built successfully

test-gradle: gradle-example
	@echo Comparing gradle build to reference...
	diff -b $(referenceModel) $(gradleModelPath)
	@echo Gradle model matches reference

test-all: test-cli test-gradle
