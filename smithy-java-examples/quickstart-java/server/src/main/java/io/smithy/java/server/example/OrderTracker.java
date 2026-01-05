/**
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: MIT-0
 */

package io.smithy.java.server.example;

import io.smithy.java.server.example.model.OrderStatus;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * This class is a stand-in for a database.
 */
final class OrderTracker {
    private static final Logger LOGGER = Logger.getLogger(OrderTracker.class.getName());
    private static final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    private static final Map<UUID, Order> ORDERS = new ConcurrentHashMap<>();

    public static void putOrder(Order order) {
        ORDERS.put(order.id(), order);

        // Start a process to complete the order in the background.
        executor.schedule(() -> completeOrder(order), 5, TimeUnit.SECONDS);
    }

    private static void completeOrder(Order order) {
        ORDERS.put(order.id(), new Order(order.id(), order.type(), OrderStatus.COMPLETED));
        LOGGER.info("Order completed: " + order.id());
    }

    public static Order getOrderById(UUID id) {
        return ORDERS.get(id);
    }
}

