package io.smithy.examples.validators;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class CommonLintingConfigurationJarTest {
    JarFile jarFile;
    Manifest manifest;

    @BeforeEach
    void setup() throws IOException {
        Path buildFolderPath = Paths.get(System.getProperty("projectUnderTest"));
        jarFile = new JarFile(buildFolderPath.resolve("libs")
            .resolve("common-linting-configuration.jar").toFile());
        manifest = jarFile.getManifest();
    }

    @Test
    public void checkJarManifest() {
        String tags = (String) manifest.getMainAttributes().get(
            new Attributes.Name("Smithy-Tags")
        );
        String[] tagValues = tags.split(", ");

        assertThat(Arrays.asList(tagValues), hasItems(
            "common-config",
            "smithy-examples.linting-and-validation-examples",
            "smithy-examples.linting-and-validation-examples:common-linting-configuration",
            "smithy-examples.linting-and-validation-examples:common-linting-configuration:unspecified",
            "validators"
        ));
    }

    @AfterEach
    void cleanup() throws IOException {
        jarFile.close();
    }
}
