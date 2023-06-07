package io.smithy.examples.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.validation.Severity;
import software.amazon.smithy.model.validation.ValidatedResult;

public class JsonNameTraitValidatorTest {
    @Test
    public void dectectsViolationModel() {
        ValidatedResult<Model> validatedResult = Model.assembler()
            .discoverModels(getClass().getClassLoader())
            .addImport(getClass().getResource("validator-test.smithy"))
            .assemble();
        var validationEvents = validatedResult.getValidationEvents();

        assertEquals(validationEvents.size(), 1);
        var validationEvent = validationEvents.get(0);
        assertEquals(validationEvent.getId(), "JsonNameTrait");
        assertEquals(validationEvent.getSeverity(), Severity.ERROR);
        assertEquals(validationEvent.getShapeId().get(), ShapeId.from("io.smithy.example.test#TestStructure$testMember"));
    }
}
