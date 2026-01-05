/**
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: MIT-0
 */

package io.smithy.kotlin.server;

import io.smithy.java.server.example.model.GetOrderInput;
import io.smithy.java.server.example.model.GetOrderOutput;
import io.smithy.java.server.example.model.OrderNotFound;
import io.smithy.java.server.example.service.GetOrderOperation;
import java.util.UUID;
import java.util.logging.Logger;
import software.amazon.smithy.java.server.RequestContext;

/**
 * Returns the specified order if found.
 */
final class GetOrder implements GetOrderOperation {
    private static final Logger LOGGER = Logger.getLogger(GetOrder.class.getName());

    @Override
    public GetOrderOutput getOrder(GetOrderInput input, RequestContext context) {
        var order = OrderTracker.getOrderById(UUID.fromString(input.id()));
        if (order == null) {
            LOGGER.warning("Order not found: " + input.id());
            throw OrderNotFound.builder()
                    .orderId(input.id())
                    .message("Order not found")
                    .build();
        }
        LOGGER.info("Order " + input.id() + " found.");
        return GetOrderOutput.builder()
                .id(input.id())
                .coffeeType(order.type())
                .status(order.status())
                .build();
    }
}
