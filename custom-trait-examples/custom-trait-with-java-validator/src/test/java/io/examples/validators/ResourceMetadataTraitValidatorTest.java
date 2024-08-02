package io.examples.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Objects;
import org.junit.jupiter.api.Test;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.validation.Severity;
import software.amazon.smithy.model.validation.ValidatedResult;

public class ResourceMetadataTraitValidatorTest {
    @Test
    public void dectectsViolationModel() {
        ValidatedResult<Model> validatedResult = Model.assembler()
            .discoverModels(getClass().getClassLoader())
            .addImport(Objects.requireNonNull(getClass().getResource("validator-test.smithy")))
            .assemble();
        var validationEvents = validatedResult.getValidationEvents();

        assertEquals(validationEvents.size(), 1);
        var validationEvent = validationEvents.get(0);
        assertEquals(validationEvent.getId(), "ResourceMetadataTrait");
        assertEquals(validationEvent.getSeverity(), Severity.ERROR);
        assertEquals(validationEvent.getShapeId().get(), ShapeId.from("example.test#TestResource"));
    }
}
