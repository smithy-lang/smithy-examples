/**
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: MIT-0
 */

package io.smithy.kotlin.server.example;

import io.smithy.kotlin.server.example.model.CoffeeType;
import io.smithy.kotlin.server.example.model.OrderStatus;
import java.util.UUID;

/**
 * A coffee drink order.
 *
 * @param id UUID of the order
 * @param type Type of drink for the order
 * @param status status of the order.
 */
public record Order(UUID id, CoffeeType type, OrderStatus status) {}
