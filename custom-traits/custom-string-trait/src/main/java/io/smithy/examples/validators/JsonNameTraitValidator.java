/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: MIT-0
 */

package io.smithy.examples.validators;

import io.smithy.examples.traits.JsonNameTrait;
import java.util.List;
import java.util.stream.Collectors;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.validation.AbstractValidator;
import software.amazon.smithy.model.validation.ValidationEvent;

public class JsonNameTraitValidator extends AbstractValidator {
    @Override
    public List<ValidationEvent> validate(Model model) {
        return model.shapes()
            .filter(shape -> shape.hasTrait(JsonNameTrait.class))
            .filter(Shape::isMemberShape)
            .filter(shape -> shape.hasTrait(software.amazon.smithy.model.traits.JsonNameTrait.class))
            .map(shape -> error(shape,
                "This shape cannot apply both the root jsonName trait and the custom jsonName trait!"))
            .collect(Collectors.toList());
    }
}
