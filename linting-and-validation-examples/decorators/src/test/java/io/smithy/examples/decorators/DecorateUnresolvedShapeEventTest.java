package io.smithy.examples.decorators;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import software.amazon.smithy.model.SourceLocation;
import software.amazon.smithy.model.validation.Severity;
import software.amazon.smithy.model.validation.ValidationEvent;
import software.amazon.smithy.model.validation.ValidationEventDecorator;

import java.util.Arrays;
import java.util.Collection;

import static io.smithy.examples.decorators.DecorateUnresolvedTraitEventTest.UNRESOLVED_TRAIT;
import static io.smithy.examples.decorators.DecorateUnresolvedTraitEventTest.unresolvedTrait;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DecorateUnresolvedShapeEventTest {

    static final String UNRESOLVED_SHAPE = "Target.UnresolvedShape";

    @ParameterizedTest
    @MethodSource("parameters")
    void runTestCase(TestCase testCase) {
        ValidationEventDecorator decorator = new DecorateUnresolvedShapeEvent();
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
                test().eventId(UNRESOLVED_SHAPE)
                        .eventMessage(unresolvedShape("smithy.framework#ValidationException"))
                        .canDecorate()
                        .shouldDecorate()
                        .build(),
                test().eventId(UNRESOLVED_SHAPE)
                        .eventMessage(unresolvedShape("smithy.example#Weather"))
                        .canDecorate()
                        .shouldNotDecorate()
                        .build(),
                test().eventId(UNRESOLVED_TRAIT)
                        .eventMessage(unresolvedTrait("aws.api#arn"))
                        .cannotDecorate()
                        .shouldNotDecorate()
                        .build()
        );
    }

    static String unresolvedShape(String shapeId) {
        return String.format("operation shape has an `error` relationship to an unresolved shape `%s`", shapeId);
    }

    static TestCaseBuilder test() {
        return new TestCaseBuilder();
    }

    static class TestCaseBuilder {
        private ValidationEvent.Builder builder = ValidationEvent.builder();
        private boolean shouldDecorate;
        private boolean canDecorate = true;

        TestCaseBuilder() {
            builder.id("Target.UnresolvedShape")
                    .sourceLocation(SourceLocation.none())
                    .severity(Severity.ERROR);
        }

        TestCaseBuilder eventId(String eventId) {
            builder.id(eventId);
            return this;
        }

        TestCaseBuilder eventMessage(String message) {
            builder.message(message);
            return this;
        }

        TestCaseBuilder shouldDecorate() {
            shouldDecorate = true;
            return this;
        }

        TestCaseBuilder shouldNotDecorate() {
            shouldDecorate = false;
            return this;
        }

        TestCaseBuilder cannotDecorate() {
            canDecorate = false;
            return this;
        }

        TestCaseBuilder canDecorate() {
            canDecorate = true;
            return this;
        }

        TestCase build() {
            return new TestCase(this);
        }
    }

    static class TestCase {
        final ValidationEvent event;
        final boolean shouldDecorate;
        final boolean canDecorate;

        TestCase(TestCaseBuilder builder) {
            this.event = builder.builder.build();
            this.shouldDecorate = builder.shouldDecorate;
            this.canDecorate = builder.canDecorate;
        }
    }
}
