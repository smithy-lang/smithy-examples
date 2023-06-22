/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: MIT-0
 */
package io.smithy.examples.decorators;

import software.amazon.smithy.model.validation.ValidationEvent;
import software.amazon.smithy.model.validation.ValidationEventDecorator;

import java.util.Map;

/**
 * Decorates events for unresolved traits events. Finds if the message contains a known traits namespace and if so adds
 * a hint to the event to point that the user might be missing the corresponding package.
 */
public class DecorateUnresolvedTraitEvent implements ValidationEventDecorator {
    private static final String UNRESOLVED_TRAIT_EVENT_ID = "Model.UnresolvedTrait";

    // Lazy initialization holder class idiom to hold static values for this class.
    // See https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
    static class LazyInitHolder {
        private static final Map<String, String> TRAIT_TO_KNOWN_PACKAGES = knownNamespacesToPackage();
    }

    /**
     * Creates a new instance of the DecorateUnresolvedTraitEvent class.
     */
    public DecorateUnresolvedTraitEvent() {
    }

    /**
     * Returns true if this decorator class "knows" how to decorate this event. Usually this involves looking at the
     * event id. For this case validation events with id "Model.UnresolvedTrait".
     *
     * @param event The event to test against
     * @return Whether this decorator "knows" how to decorate this event.
     */
    @Override
    public boolean canDecorate(ValidationEvent event) {
        return event.containsId(UNRESOLVED_TRAIT_EVENT_ID);
    }

    /**
     * Decorates this event if the unresolved trait can be mapped to a known package, otherwise returns the event
     * unchanged.
     *
     * @param event The event to decorate
     * @return The decorated event if the unresolved trait is known or the unmodified event otherwise.
     */
    @Override
    public ValidationEvent decorate(ValidationEvent event) {
        String missingDependency = findPackageIncludingTrait(event.getMessage());
        if (missingDependency != null) {
            return event.toBuilder()
                    .hint(String.format("You might be missing a dependency to the package `%s`."
                                    + " Try adding `implementation(\"%1$s:$smithyVersion\")` to the `dependencies` section"
                                    + " of your `build.gradle.kts` file",
                            missingDependency))
                    .build();
        }
        return event;
    }

    private String findPackageIncludingTrait(String message) {
        for (Map.Entry<String, String> kvp : LazyInitHolder.TRAIT_TO_KNOWN_PACKAGES.entrySet()) {
            if (message.contains(kvp.getKey())) {
                return kvp.getValue();
            }
        }
        return null;
    }

    private static Map<String, String> knownNamespacesToPackage() {
        return Map.of(
                // AWS Traits
                "aws.api#", "smithy-aws-traits",
                "aws.auth#", "smithy-aws-traits",
                "aws.customizations#", "smithy-aws-traits",
                "aws.protocols#", "smithy-aws-traits",
                // API Gateway
                "aws.apigateway#", "smithy-aws-apigateway-traits",
                // Cloud Formation
                "aws.cloudformation#", "smithy-aws-cloudformation-traits",
                // IAM
                "aws.iam#", "smithy-aws-iam-traits",
                // Mqtt
                "aws.mqtt#", "smithy-mqtt-traits",
                // Rules Engine
                "smithy.rules#", "smithy-rules-engine",
                // Waiters
                "smithy.waiters#", "smithy-waiters");
    }
}
