package io.smithy.examples.validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.validation.Severity;
import software.amazon.smithy.model.validation.ValidationEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomValidatorValidationTest {

    static File smithyBuildInfo;
    static List<ValidationEvent> validationEvents;

    @BeforeAll
    static void setup() throws FileNotFoundException {
        Path buildFolderPath = Paths.get(System.getProperty("buildFolder"));
        smithyBuildInfo = buildFolderPath.resolve("smithyprojections")
            .resolve(System.getProperty("projectName"))
            .resolve("source")
            .resolve("build-info")
            .resolve("smithy-build-info.json")
            .toFile();
        var jsonOutput = Node.parse(new FileInputStream(smithyBuildInfo)).expectObjectNode();
        validationEvents = jsonOutput.expectMember("validationEvents")
            .expectArrayNode()
            .getElementsAs(ValidationEvent::fromNode);
    }

    @Test
    public void correctNumberOfValidations() {
        var expectedMatches = 1;
        assertEquals(validationEvents.size(), expectedMatches);
    }

    @Test
    public void correctSeverityOfValidations() {
        assertTrue(validationEvents.stream()
            .allMatch(event -> event.getSeverity().equals(Severity.WARNING))
        );
    }

    @Test
    public void correctValidationValues() {
        var validationEvent = validationEvents.get(0);
        assertEquals(validationEvent.getId(), "Documentation");
        assertEquals(validationEvent.getMessage(), "This shape is not documented!");
    }
}
