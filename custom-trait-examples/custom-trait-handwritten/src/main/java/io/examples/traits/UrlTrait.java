/**
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: MIT-0
 */

package io.examples.traits;

import java.net.MalformedURLException;
import java.net.URL;
import software.amazon.smithy.model.FromSourceLocation;
import software.amazon.smithy.model.SourceLocation;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.traits.StringTrait;

public final class UrlTrait extends StringTrait {

    public static final ShapeId ID = ShapeId.from("example.traits#url");

    private final URL urlValue;

    private UrlTrait(String value) {
        super(ID, value, SourceLocation.NONE);
        this.urlValue = getURL(value);
    }

    private UrlTrait(String value, FromSourceLocation sourceLocation) {
        super(ID, value, sourceLocation);
        this.urlValue = getURL(value);
    }

    public URL getUrlValue() {
        return urlValue;
    }

    private static URL getURL(String value) {
        try {
            return new URL(value);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not parse URL: " + value, e);
        }
    }

    public static final class Provider extends StringTrait.Provider<UrlTrait> {
        public Provider() {
            super(ID, UrlTrait::new);
        }
    }
}
