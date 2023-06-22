package io.smithy.examples.decorators;

import org.junit.jupiter.api.Test;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.loader.ModelAssembler;
import software.amazon.smithy.model.validation.ValidatedResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DecoratorsTest {

    @Test
    public void addsHintWhenUnknownAwsJsonProtocolTraitIsEncounter() {
        ValidatedResult<Model> result =
                new ModelAssembler()
                        .addImport(getClass().getResource("unknown-aws-protocol.smithy"))
                        .assemble();
        assertTrue(result.isBroken());
        assertEquals(result.getValidationEvents().size(), 1);
        assertTrue(result.getValidationEvents().get(0).getHint().isPresent());
        assertTrue(result.getValidationEvents().get(0).getHint().get().contains("smithy-aws-traits"));
    }

    @Test
    public void addsHintWhenUnknownValidationShapeIsEncounter() {
        ValidatedResult<Model> result =
                new ModelAssembler()
                        .addImport(getClass().getResource("unknown-validation-exception-shape.smithy"))
                        .assemble();
        assertTrue(result.isBroken());
        assertEquals(result.getValidationEvents().size(), 1);
        assertTrue(result.getValidationEvents().get(0).getHint().isPresent());
        assertTrue(result.getValidationEvents().get(0).getHint().get().contains("smithy-validation-model"));
    }
}
