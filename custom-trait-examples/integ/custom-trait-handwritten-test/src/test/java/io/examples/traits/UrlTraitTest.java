package io.examples.traits;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;

public class UrlTraitTest {
    @Test
    public void loadsFromModel() throws MalformedURLException {
        Model result = Model.assembler()
            .discoverModels(getClass().getClassLoader())
            .addImport(Objects.requireNonNull(getClass().getResource("custom-trait-test.smithy")))
            .assemble()
            .unwrap();

        Shape member = result.expectShape(ShapeId.from("example.test#TestStruct$member"));
        assertTrue(member.hasTrait(UrlTrait.class));
        var trait = member.getTrait(UrlTrait.class).get();
        assertEquals(trait.getUrlValue(), new URL("https://example.com/"));
    }
}
