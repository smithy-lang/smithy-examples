/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: MIT-0
 */

package io.smithy.examples.validators;

import java.util.List;
import java.util.stream.Collectors;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.loader.Prelude;
import software.amazon.smithy.model.traits.DocumentationTrait;
import software.amazon.smithy.model.validation.AbstractValidator;
import software.amazon.smithy.model.validation.ValidationEvent;

public class DocumentationValidator extends AbstractValidator {
    @Override
    public List<ValidationEvent> validate(Model model) {
        return model.shapes()
            .filter(shape -> !Prelude.isPreludeShape(shape.getId()))
            .filter(shape -> !shape.hasTrait(DocumentationTrait.class))
            .map(shape -> warning(shape, "This shape is not documented!"))
            .collect(Collectors.toList());
    }
}
