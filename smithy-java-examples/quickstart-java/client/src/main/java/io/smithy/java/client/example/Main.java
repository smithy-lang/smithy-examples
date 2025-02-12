package io.smithy.java.client.example;

import io.smithy.java.client.example.client.CoffeeShopClient;
import io.smithy.java.client.example.model.CoffeeType;
import io.smithy.java.client.example.model.CreateOrderInput;
import io.smithy.java.client.example.model.GetOrderInput;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import software.amazon.smithy.java.client.core.endpoint.EndpointResolver;


/**
 * Entrypoint for executing cafe client commands
 */
public final class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final CoffeeShopClient client =  CoffeeShopClient.builder()
            .endpointResolver(EndpointResolver.staticEndpoint("http://localhost:8888"))
            .build();

    public static void main(String[] args) throws InterruptedException {
        // Create an order request
        var createRequest = CreateOrderInput.builder()
                .coffeeType(CoffeeType.COLD_BREW)
                .build();

        // Call the service to create an order
        var createResponse = client.createOrder(createRequest);
        LOGGER.info("Created request with id = " + createResponse.id());

        // Get the order. Should still be in progress.
        var getRequest = GetOrderInput.builder().id(createResponse.id()).build();
        var getResponse1 = client.getOrder(getRequest);
        LOGGER.info("Got order with id = " + getResponse1.id());

        // Give order some time to complete
        LOGGER.info("Waiting for order to complete....");
        TimeUnit.SECONDS.sleep(5);

        // Get the order again.
        var getResponse2 = client.getOrder(getRequest);
        LOGGER.info("Completed Order :" + getResponse2);
    }
}
