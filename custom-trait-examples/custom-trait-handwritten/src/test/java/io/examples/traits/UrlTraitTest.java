package io.examples.traits;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;
import org.junit.jupiter.api.Test;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.validation.Severity;
import software.amazon.smithy.model.validation.ValidatedResult;

public class UrlTraitTest {

    @Test
    public void loadsFromModel() {
        ValidatedResult<Model> validatedResult = Model.assembler()
                .discoverModels(getClass().getClassLoader())
                .addImport(Objects.requireNonNull(getClass().getResource("valid-url.smithy")))
                .assemble();
        assertEquals(validatedResult.getValidationEvents().size(), 0);
    }

    @Test
    public void failsOnInvalidUrl() {
        ValidatedResult<Model> validatedResult = Model.assembler()
                .discoverModels(getClass().getClassLoader())
                .addImport(Objects.requireNonNull(getClass().getResource("invalid-url.smithy")))
                .assemble();
        var validationEvents = validatedResult.getValidationEvents();
        assertEquals(validationEvents.size(), 1);
        var validationEvent = validationEvents.get(0);
        assertEquals(validationEvent.getId(), "Model");
        assertEquals(validationEvent.getShapeId().get(), ShapeId.from("example.test#TestStructure$member"));
        assertEquals(validationEvent.getSeverity(), Severity.ERROR);
        assertTrue(validationEvent.getMessage().contains("Could not parse URL: badUrl:bad"));
        System.out.println(validationEvent);
    }
}
