import { CoffeeShop, CoffeeType, CreateOrderInput, GetOrderInput }
    from '@com.example/coffee-shop-client'

const client = new CoffeeShop({
    endpoint: {
        protocol: 'http',
        hostname: 'localhost',
        port: 8888,
        path: '/'
    }
})

async function main() {
    try {
        // Create an order request
        const createRequest: CreateOrderInput = {
            coffeeType: CoffeeType.COLD_BREW
        };

        // Call the service to create an order
        const createResponse = await client.createOrder(createRequest);
        console.log(`Created request with id = ${createResponse.id}`);

        // Get the order. Should still be in progress.
        const getRequest: GetOrderInput = { id: createResponse.id };
        const getResponse1 = await client.getOrder(getRequest);
        console.log(`Got order with id = ${getResponse1.id}`);

        // Give order some time to complete
        console.log("Waiting for order to complete....");
        await new Promise(resolve => setTimeout(resolve, 5000)); // 5 seconds

        // Get the order again.
        const getResponse2 = await client.getOrder(getRequest);
        console.log("Completed Order:", getResponse2);
    } catch (error) {
        console.error("An error occurred:", error);
    }
}

main().catch(error => console.error("Unhandled error:", error));

