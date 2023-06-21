BASE_DIR=.

test-all-make:
	@echo running all make tests...
	for file in $$(find ${BASE_DIR}/*/ -type f -name Makefile) ; do \
		(cd $$(dirname $$file) ; make test-all) ; \
		if [ ! $$? = 0 ]; then \
			echo "Make build for example $$file failed." ; \
			exit 1; \
		fi \
	done
	@echo make tests completed!

test-all-gradle:
	@echo running all gradle tests...
	./gradlew clean build test
	@echo ran all gradle tests

test-all: test-all-make test-all-gradle
	@echo all tests run