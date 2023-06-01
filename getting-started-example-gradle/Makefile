smithyVersion = 1.31.0

clean:
	rm -rf weather-service/build/

build: clean
	@echo Building getting-started-gradle-example...
	(cd weather-service; ./gradlew clean build)
	@echo getting-started-gradle-example built successfully

test: build