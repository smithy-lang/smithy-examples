package io.smithy.examples.traits;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;

public class ResourceMetadataTraitTest {
    @Test
    public void loadsFromModel() {
        Model result = Model.assembler()
            .discoverModels(getClass().getClassLoader())
            .addImport(getClass().getResource("custom-trait-test.smithy"))
            .assemble()
            .unwrap();

        Shape superResource = result.expectShape(ShapeId.from("io.smithy.example.test#TestResource"));

        assertTrue(superResource.hasTrait(ResourceMetadataTrait.class));
        var traitInstance = superResource.expectTrait(ResourceMetadataTrait.class);
        assertEquals(traitInstance.getDescription(), "Description");
        assertEquals(traitInstance.getType(), "SPECIAL");
        var associatedStructs = traitInstance.getAssociatedStructures();
        assertEquals(associatedStructs.size(), 1);
        assertEquals(associatedStructs.get(0), ShapeId.from("io.smithy.example.test#AssociatedStruct"));
    }
}
