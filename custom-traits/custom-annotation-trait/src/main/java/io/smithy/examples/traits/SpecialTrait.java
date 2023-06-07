/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package io.smithy.examples.traits;

import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.traits.AnnotationTrait;

public final class SpecialTrait extends AnnotationTrait {

    public static final ShapeId ID = ShapeId.from("smithy.example#special");

    public SpecialTrait(ObjectNode node) {
        super(ID, node);
    }

    public SpecialTrait() {
        this(Node.objectNode());
    }

    public static final class Provider extends AnnotationTrait.Provider<SpecialTrait> {
        public Provider() {
            super(ID, SpecialTrait::new);
        }
    }
}
