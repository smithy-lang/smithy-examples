/**
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: MIT-0
 */

package io.smithy.java.server.example;

import io.smithy.java.server.example.model.CreateOrderInput;
import io.smithy.java.server.example.model.CreateOrderOutput;
import io.smithy.java.server.example.model.OrderStatus;
import io.smithy.java.server.example.service.CreateOrderOperation;
import java.util.UUID;

import java.util.logging.Logger;
import software.amazon.smithy.java.server.RequestContext;

/**
 * Create an order for a coffee.
 */
final class CreateOrder implements CreateOrderOperation {
    private static final Logger LOGGER = Logger.getLogger(CreateOrder.class.getName());

    @Override
    public CreateOrderOutput createOrder(CreateOrderInput input, RequestContext context) {
        var id = UUID.randomUUID();
        OrderTracker.putOrder(new Order(id, input.coffeeType(), OrderStatus.IN_PROGRESS));

        LOGGER.info("Created order " + id + " for a " + input.coffeeType());

        return CreateOrderOutput.builder()
                .id(id.toString())
                .coffeeType(input.coffeeType())
                .status(OrderStatus.IN_PROGRESS)
                .build();
    }
}
