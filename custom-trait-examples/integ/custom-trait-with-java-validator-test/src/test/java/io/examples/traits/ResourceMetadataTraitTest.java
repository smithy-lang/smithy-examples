package io.examples.traits;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;
import org.junit.jupiter.api.Test;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;

public class ResourceMetadataTraitTest {
    @Test
    public void loadsFromModel() {
        Model result = Model.assembler()
            .discoverModels(getClass().getClassLoader())
            .addImport(Objects.requireNonNull(getClass().getResource("custom-trait-test.smithy")))
            .assemble()
            .unwrap();

        Shape superResource = result.expectShape(ShapeId.from("example.test#Forecast"));

        assertTrue(superResource.hasTrait(ResourceMetadataTrait.class));
        var traitInstance = superResource.expectTrait(ResourceMetadataTrait.class);
        assertEquals(traitInstance.getDescription(), "description");
        assertEquals(traitInstance.getType(), ResourceType.NORMAL);
        var associatedStructs = traitInstance.getAssociatedStructures();
        assertTrue(associatedStructs.isPresent());
        assertEquals(associatedStructs.get().size(), 1);
        assertEquals(associatedStructs.get().get(0), ShapeId.from("example.test#ForecastStruct"));
    }
}
