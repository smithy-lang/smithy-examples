package com.example.cafe;

import java.util.List;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.traits.ExternalDocumentationTrait;
import software.amazon.smithy.model.validation.AbstractValidator;
import software.amazon.smithy.model.validation.ValidationEvent;
import software.amazon.smithy.model.validation.ValidatorService;

public final class DocUrlValidator extends AbstractValidator {
    private static final String CAFE_SITE_NAME = "cafe.example.smithy.io";

    public static final class Provider extends ValidatorService.Provider {
        public Provider() {
            super(DocUrlValidator.class, DocUrlValidator::new);
        }
    }

    @Override
    public List<ValidationEvent> validate(Model model) {
        // Find every shape that violates the linter and return a list 
        // of ValidationEvents.
        return model.shapes()
            .filter(shape -> shape.hasTrait(ExternalDocumentationTrait.class))
            .flatMap(shape -> shape.expectTrait(ExternalDocumentationTrait.class)
                // Get a stream of Name -> URL entries
                .getUrls().entrySet().stream()
                // Get all URLs that do not match expected internal site name
                .filter(entry -> !entry.getValue().contains(CAFE_SITE_NAME))
                // Emit a helpful warning message for each invalid URL
                .map(entry -> warning(shape, String.format("External url `%s` for `%s` is invalid." 
                        + " Expected URL to start with " + CAFE_SITE_NAME, entry.getValue(), entry.getKey())))
            ).toList();
    }
}
