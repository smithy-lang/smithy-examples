/**
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: MIT-0
 */

package io.smithy.java.client.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.smithy.java.client.example.client.CoffeeShopClient;
import io.smithy.java.client.example.model.CoffeeType;
import io.smithy.java.client.example.model.CreateOrderInput;
import io.smithy.java.client.example.model.GetMenuInput;
import io.smithy.java.client.example.model.GetOrderInput;
import io.smithy.java.client.example.model.OrderNotFound;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;


public class TestRunner {
    private final CoffeeShopClient client = CoffeeShopClient.builder().build();

    @Test
    public void getMenu() {
        var menu = client.getMenu(GetMenuInput.builder().build());
        System.out.println(menu);
    }

    @Test
    public void createOrder() throws InterruptedException {
        // Create an order
        var createRequest = CreateOrderInput.builder().coffeeType(CoffeeType.COLD_BREW).build();
        var createResponse = client.createOrder(createRequest);
        System.out.println("Created request with id = " + createResponse.id());

        // Get the order. Should still be in progress.
        var getRequest = GetOrderInput.builder().id(createResponse.id()).build();
        var getResponse1 = client.getOrder(getRequest);
        System.out.println("Got order with id = " + getResponse1.id());

        // Give order some time to complete
        System.out.println("Waiting for order to complete....");
        TimeUnit.SECONDS.sleep(5);

        // Get the order again.
        var getResponse2 = client.getOrder(getRequest);
        System.out.println("Completed Order :" + getResponse2);
    }

    @Test
    void errorsOutIfOrderDoesNotExist() throws InterruptedException {
        var getRequest = GetOrderInput.builder().id(UUID.randomUUID().toString()).build();
        var orderNotFound = assertThrows(OrderNotFound.class, () -> client.getOrder(getRequest));
        assertEquals(orderNotFound.orderId(), getRequest.id());
    }
}
