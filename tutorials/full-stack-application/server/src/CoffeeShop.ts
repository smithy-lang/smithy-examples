import { CoffeeShopService, CoffeeType, CreateOrderServerInput, CreateOrderServerOutput, GetMenuServerInput, GetMenuServerOutput, GetOrderServerInput, GetOrderServerOutput, OrderNotFound, OrderStatus } from "@com.example/coffee-service-server";
import { randomUUID } from "crypto";

// An implementation of the service from the SSDK
export class CoffeeShop implements CoffeeShopService<{}> {
    // Set up a map for storing order information
    orders = new Map<string, OrderData>();
    // Set up an orders queue for handling orders
    queue: OrderData[] = []

    CreateOrder = async (input: CreateOrderServerInput): Promise<CreateOrderServerOutput> => {
        console.log("received an order request...")
        const order = {
            orderId: randomUUID(),
            coffeeType: input.coffeeType,
            status: OrderStatus.IN_PROGRESS
        }

        this.orders.set(order.orderId, order)
        this.queue.push(order)

        console.log(`created order: ${JSON.stringify(order)}`)
        return {
            id: order.orderId,
            coffeeType: order.coffeeType,
            status: order.status
        }
    }

    GetMenu = async (input: GetMenuServerInput): Promise<GetMenuServerOutput> => {
        console.log("getting menu...")
        return {
            items: [
                {
                    type: CoffeeType.DRIP,
                    description: "A clean-bodied, rounder, and more simplistic flavour profile.\n" +
                        "Often praised for mellow and less intense notes.\n" +
                        "Far less concentrated than espresso."
                },
                {
                    type: CoffeeType.POUR_OVER,
                    description: "Similar to drip coffee, but with a process that brings out more subtle nuances in flavor.\n" +
                        "More concentrated than drip, but less than espresso."
                },
                {
                    type: CoffeeType.LATTE,
                    description: "A creamier, milk-based drink made with espresso.\n" +
                        "A subtle coffee taste, with smooth texture.\n" +
                        "High milk-to-coffee ratio."
                },
                {
                    type: CoffeeType.ESPRESSO,
                    description: "A highly concentrated form of coffee, brewed under high pressure.\n" +
                        "Syrupy, thick liquid in a small serving size.\n" +
                        "Full bodied and intensely aromatic."
                },
                {
                    type: CoffeeType.COLD_BREW,
                    description: "A high-extraction and chilled form of coffee that has been cold-pressed..\n" +
                        "Different flavor profile than other hot methods of brewing.\n" +
                        "Smooth and slightly more caffeinated as a result of its concentration."
                }
            ]
        }
    }

    GetOrder = async (input: GetOrderServerInput): Promise<GetOrderServerOutput> => {
        console.log(`getting an order (${input.id})...`)
        if (this.orders.has(input.id)) {
            const order = this.orders.get(input.id)
            return {
                id: order.orderId,
                coffeeType: order.coffeeType,
                status: order.status
            }
        } else {
            console.log(`order (${input.id}) does not exist.`)
            throw new OrderNotFound({
                message: `order ${input.id} not found.`
            })
        }
    }

    // Handle orders as they come in (FIFO), marking them completed based on some random
    // timing (to simulate a delay)
    handleOrders = async () => {
        console.log("handling orders...")
        while (true) {
            await new Promise(resolve => setTimeout(resolve, Math.random() * 1000 + 1000));
            let order = this.queue.shift()
            if (order != null) {
                order.status = OrderStatus.COMPLETED
                console.log(`order ${order.orderId} is completed.`)  
            }
        }
    }
}

// A data object to hold order data
interface OrderData {
    orderId: string
    coffeeType: CoffeeType;
    status: OrderStatus;
}
