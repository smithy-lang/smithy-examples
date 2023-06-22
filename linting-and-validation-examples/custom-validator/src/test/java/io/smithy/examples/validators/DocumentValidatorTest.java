package io.smithy.examples.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.validation.Severity;
import software.amazon.smithy.model.validation.ValidatedResult;

public class DocumentValidatorTest {
    @Test
    public void dectectsViolationModel() {
        ValidatedResult<Model> validatedResult = Model.assembler()
            .discoverModels(getClass().getClassLoader())
            .addImport(getClass().getResource("validator-test.smithy"))
            .assemble();
        var validationEvents = validatedResult.getValidationEvents();

        assertEquals(validationEvents.size(), 2);
        var validationEvent1 = validationEvents.get(0);
        assertEquals(validationEvent1.getId(), "Documentation");
        assertEquals(validationEvent1.getSeverity(), Severity.WARNING);
        assertEquals(validationEvent1.getShapeId().get(), ShapeId.from("io.smithy.example.test#JustAnUndocumentedShape"));

        var validationEvent2 = validationEvents.get(1);
        assertEquals(validationEvent2.getId(), "Documentation");
        assertEquals(validationEvent2.getSeverity(), Severity.WARNING);
        assertEquals(validationEvent2.getShapeId().get(), ShapeId.from("io.smithy.example.test#AnotherUndocumentedShape"));
    }
}
