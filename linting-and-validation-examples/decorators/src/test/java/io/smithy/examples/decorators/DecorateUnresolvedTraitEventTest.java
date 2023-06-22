package io.smithy.examples.decorators;

import io.smithy.examples.decorators.DecorateUnresolvedShapeEventTest.TestCase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import software.amazon.smithy.model.validation.ValidationEvent;
import software.amazon.smithy.model.validation.ValidationEventDecorator;

import java.util.Arrays;
import java.util.Collection;

import static io.smithy.examples.decorators.DecorateUnresolvedShapeEventTest.UNRESOLVED_SHAPE;
import static io.smithy.examples.decorators.DecorateUnresolvedShapeEventTest.test;
import static io.smithy.examples.decorators.DecorateUnresolvedShapeEventTest.unresolvedShape;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DecorateUnresolvedTraitEventTest {
    static final String UNRESOLVED_TRAIT = "Model.UnresolvedTrait";

    @ParameterizedTest
    @MethodSource("parameters")
    void runTestCase(TestCase testCase) {
        ValidationEventDecorator decorator = new DecorateUnresolvedTraitEvent();
        assertEquals(testCase.canDecorate, decorator.canDecorate(testCase.event));
        ValidationEvent decorated = decorator.decorate(testCase.event);
        if (testCase.shouldDecorate) {
            assertTrue(decorated.getHint().isPresent());
        } else {
            assertFalse(decorated.getHint().isPresent());
        }
    }

    static Collection<TestCase> parameters() {
        return Arrays.asList(
                test().eventId(UNRESOLVED_TRAIT)
                        .eventMessage(unresolvedTrait("aws.api#arn"))
                        .canDecorate()
                        .shouldDecorate()
                        .build(),
                test().eventId(UNRESOLVED_TRAIT)
                        .eventMessage(unresolvedTrait("aws.api#service"))
                        .canDecorate()
                        .shouldDecorate()
                        .build(),
                test().eventId(UNRESOLVED_TRAIT)
                        .eventMessage(unresolvedTrait("aws.auth#sigv4"))
                        .canDecorate()
                        .shouldDecorate()
                        .build(),
                test().eventId(UNRESOLVED_TRAIT)
                        .eventMessage(unresolvedTrait("aws.protocols#restJson1"))
                        .canDecorate()
                        .shouldDecorate()
                        .build(),
                test().eventId(UNRESOLVED_SHAPE)
                        .eventMessage(unresolvedShape("smithy.framework#ValidationException"))
                        .cannotDecorate()
                        .shouldNotDecorate()
                        .build(),
                test().eventId(UNRESOLVED_SHAPE)
                        .eventMessage(unresolvedShape("smithy.example#Weather"))
                        .cannotDecorate()
                        .shouldNotDecorate()
                        .build()
        );
    }

    static String unresolvedTrait(String shapeId) {
        return String.format("Unable to resolve trait `%s`. If this is a custom trait,"
                + " then it must be defined before it can be used in a model.", shapeId);
    }
}
