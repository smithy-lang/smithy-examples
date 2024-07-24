package io.examples.traits;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;
import org.junit.jupiter.api.Test;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;

public class JsonNameTraitTest {
    @Test
    public void loadsFromModel() {
        Model result = Model.assembler()
            .discoverModels(getClass().getClassLoader())
            .addImport(Objects.requireNonNull(getClass().getResource("custom-trait-test.smithy")))
            .assemble()
            .unwrap();

        Shape superResource = result.expectShape(ShapeId.from("example.test#TestStructure$testMember"));

        assertTrue(superResource.hasTrait(JsonNameTrait.class));
        var traitInstance = superResource.expectTrait(JsonNameTrait.class);
        assertEquals(traitInstance.getValue(), "TESTING");
    }
}
