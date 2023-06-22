package io.smithy.examples.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

public class CommonLintingConfigurationValidationTest {

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
        var expectedMatches = 5;
        assertEquals(validationEvents.size(), expectedMatches);
    }

    @Test
    public void correctSeverityOfValidations() {
        assertTrue(validationEvents.stream()
            .allMatch(event -> event.getSeverity().equals(Severity.WARNING))
        );
    }

    @Test
    public void correctTypesOfValidations() {
        assertEquals(validationEvents.stream()
            .filter(event -> event.getId().equals("AbbreviationName")).count(), 1);
        assertEquals(validationEvents.stream()
            .filter(event -> event.getId().equals("CamelCase")).count(), 1);
        assertEquals(validationEvents.stream()
            .filter(event -> event.getId().equals("MissingSensitiveTrait")).count(), 1);
        assertEquals(validationEvents.stream()
            .filter(event -> event.getId().equals("OperationInputName")).count(), 1);
        assertEquals(validationEvents.stream()
            .filter(event -> event.getId().equals("OperationErrorName")).count(), 1);
    }
}
