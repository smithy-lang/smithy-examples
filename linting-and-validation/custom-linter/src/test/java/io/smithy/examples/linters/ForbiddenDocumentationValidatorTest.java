package io.smithy.examples.linters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.validation.Severity;
import software.amazon.smithy.model.validation.ValidatedResult;


public class ForbiddenDocumentationValidatorTest {
    @Test
    public void loadsFromModel() {
        ValidatedResult<Model> result = Model.assembler()
            .discoverModels(getClass().getClassLoader())
            .addImport(getClass().getResource("linter-execution-test.smithy"))
            .assemble();

        var validationEvents = result.getValidationEvents();
        assertEquals(validationEvents.size(), 1);
        var validationEvent = validationEvents.get(0);
        assertEquals(validationEvent.getId(), "ForbiddenDocumentation");
        assertEquals(validationEvent.getSeverity(), Severity.DANGER);
        assertEquals(validationEvent.getShapeId().get(), ShapeId.from("smithy.example.test#BadAbbreviationShapeID"));
    }
}
