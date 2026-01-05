package io.smithy.kotlin.client.example

import aws.smithy.kotlin.runtime.client.endpoints.Endpoint
import io.smithy.kotlin.client.example.endpoints.CoffeeShopEndpointProvider
import io.smithy.kotlin.client.example.model.CoffeeType
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.logging.Logger
import kotlin.time.Duration.Companion.seconds

fun main(): Unit = runBlocking {
    val logger: Logger = Logger.getLogger("SmithyKotlinClient")

    CoffeeShopClient {
        endpointProvider = CoffeeShopEndpointProvider {
            Endpoint("http://localhost:8888")
        }
    }.use { client ->
        logger.info("Creating coffee order")

        val createOrderResponse = client.createOrder {
            coffeeType = CoffeeType.Latte
        }

        logger.info("Created order with ID: ${createOrderResponse.id}")

        logger.info("Waiting for order to complete.")
        delay(5.seconds)

        logger.info("Checking order status")
        val orderStatus = client.getOrder {
            id = createOrderResponse.id
        }.status

        logger.info("Order status: $orderStatus")
    }
}
