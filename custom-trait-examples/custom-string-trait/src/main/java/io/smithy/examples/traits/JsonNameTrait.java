/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: MIT-0
 */

package io.smithy.examples.traits;

import software.amazon.smithy.model.FromSourceLocation;
import software.amazon.smithy.model.SourceLocation;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.traits.StringTrait;

public final class JsonNameTrait extends StringTrait {

    public static final ShapeId ID = ShapeId.from("io.smithy.example#jsonName");

    private JsonNameTrait(String name) {
        super(ID, name, SourceLocation.NONE);
    }

    private JsonNameTrait(String name, FromSourceLocation sourceLocation) {
        super(ID, name, sourceLocation);
    }

    public static final class Provider extends StringTrait.Provider<JsonNameTrait> {
        public Provider() {
            super(ID, JsonNameTrait::new);
        }
    }
}
