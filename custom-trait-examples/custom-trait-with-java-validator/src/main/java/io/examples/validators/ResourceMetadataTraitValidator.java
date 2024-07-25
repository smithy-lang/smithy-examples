/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: MIT-0
 */

package io.examples.validators;

import io.examples.traits.ResourceMetadataTrait;
import java.util.List;
import java.util.stream.Collectors;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.loader.Prelude;
import software.amazon.smithy.model.validation.AbstractValidator;
import software.amazon.smithy.model.validation.ValidationEvent;

/**
 * Custom Java validators can apply additional model validation associated with your trait.
 * See: <a href="https://smithy.io/2.0/guides/model-linters.html#writing-custom-validators">writing custom validators</a>
 */
public class ResourceMetadataTraitValidator extends AbstractValidator {
    @Override
    public List<ValidationEvent> validate(Model model) {
        return model.shapes()
            .filter(shape -> !Prelude.isPreludeShape(shape.getId()))
            .filter(shape -> shape.hasTrait(ResourceMetadataTrait.class))
            .filter(shape -> shape.hasTrait("smithy.api#documentation"))
            .map(shape -> error(shape,
                "This shape cannot apply Resource Metadata trait to a resource with documentation"))
            .collect(Collectors.toList());
    }
}
