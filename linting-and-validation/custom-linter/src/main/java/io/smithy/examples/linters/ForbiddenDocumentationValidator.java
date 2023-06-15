/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: MIT-0
 */

package io.smithy.examples.linters;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.node.NodeMapper;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.traits.DocumentationTrait;
import software.amazon.smithy.model.validation.AbstractValidator;
import software.amazon.smithy.model.validation.ValidationEvent;
import software.amazon.smithy.model.validation.ValidatorService;

public final class ForbiddenDocumentationValidator extends AbstractValidator {

    /**
     * ForbiddenDocumentation configuration settings.
     */
    public static final class Config {
        private List<String> forbid;

        public List<String> getForbid() {
            return forbid;
        }

        public void setForbid(List<String> forbid) {
            this.forbid = forbid;
        }
    }

    // Does the actual work of converting metadata found in a Smithy
    // model into an actual implementation of a Validator.
    public static final class Provider extends ValidatorService.Provider {
        public Provider() {
            super(ForbiddenDocumentationValidator.class, configuration -> {
                // Deserialize the Node value into the Config POJO.
                NodeMapper mapper = new NodeMapper();
                ForbiddenDocumentationValidator.Config config = mapper.deserialize(configuration, Config.class);
                return new ForbiddenDocumentationValidator(config);
            });
        }
    }

    private final List<String> forbid;

    // The constructor is private since the validator is only intended to
    // be created when loading a model via the Provider class.
    private ForbiddenDocumentationValidator(Config config) {
        this.forbid = config.forbid;
    }

    @Override
    public List<ValidationEvent> validate(Model model) {
        // Find every shape that violates the linter and return a list
        // of ValidationEvents.
        return model.shapes()
            .filter(shape -> shape.hasTrait(DocumentationTrait.class))
            .flatMap(shape -> validateShape(shape).map(Stream::of).orElseGet(Stream::empty))
            .collect(Collectors.toList());
    }

    private Optional<ValidationEvent> validateShape(Shape shape) {
        // Grab the trait by type.
        DocumentationTrait trait = shape.expectTrait(DocumentationTrait.class);
        String docString = trait.getValue();

        for (String text : forbid) {
            if (docString.contains(text)) {
                // Emit an event that points at the location of the trait
                // and associates the warning with the shape.
                return Optional.of(error(shape, trait, "Documentation uses forbidden text: " + text));
            }
        }

        return Optional.empty();
    }
}
