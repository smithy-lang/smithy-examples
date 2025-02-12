/**
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: MIT-0
 */

package io.smithy.java.example.plugins;

import software.amazon.smithy.java.client.core.ClientConfig;
import software.amazon.smithy.java.client.core.ClientPlugin;
import software.amazon.smithy.java.client.core.endpoint.EndpointResolver;

/**
 * Example plugin that sets the static endpoint for the generated client.
 *
 * <p>Plugins allow clients to be configured in a repeatable way.
 */
public class ExamplePlugin implements ClientPlugin {
    private static final EndpointResolver STATIC_ENDPOINT = EndpointResolver.staticEndpoint("http://localhost:8888");

    @Override
    public void configureClient(ClientConfig.Builder builder) {
        builder.endpointResolver(STATIC_ENDPOINT);
    }
}
