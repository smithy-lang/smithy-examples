package io.smithy.examples.plugins;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.validation.Severity;
import software.amazon.smithy.model.validation.ValidationEvent;

public class PluginTutorialTest {

    static File smithyBuildInfo;
    static List<ValidationEvent> validationEvents;

    @BeforeAll
    static void setup() throws FileNotFoundException {
        Path buildFolderPath = Paths.get(System.getProperty("buildFolder"));
        System.out.println(buildFolderPath);
        smithyBuildInfo = buildFolderPath.resolve("smithyprojections")
            .resolve("service")
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
    public void correctTypesOfValidations() {
        assertEquals(validationEvents.stream()
                .peek(System.out::println)
            .filter(event -> event.getId().equals("DocUrl")).count(), 1);
    }
}
