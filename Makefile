BASE_DIR=.

test-all-make:
	@echo running all make tests...
	for file in $$(find ${BASE_DIR}/*/ -type f -name Makefile) ; do \
		(cd $$(dirname $$file) ; make test) ; \
		if [ ! $$? = 0 ]; then \
			echo "Make build for example $$file failed." ; \
			exit 1; \
		fi \
	done
	@echo make tests completed!

test-all-gradle:
	@echo running all gradle tests...
	for file in $$(find ${BASE_DIR} -type f -name gradlew) ; do \
		(cd $$(dirname $$file) ; ./gradlew clean build test) ; \
		if [ ! $$? = 0 ]; then \
			echo "Gradle build for example $$file failed." ; \
			exit 1 ; \
		fi \
	done
	@echo ran all gradle tests


test-all: test-all-make test-all-gradle
	@echo all tests run