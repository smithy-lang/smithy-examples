import { CoffeeServiceService, CoffeeType, CreateOrderServerInput, CreateOrderServerOutput, GetMenuServerInput, GetMenuServerOutput, GetOrderServerInput, GetOrderServerOutput, OrderNotFound, OrderStatus } from "@com.example/coffee-service-server";
import { randomUUID } from "crypto";

// An implementation of the service from the SSDK
export class CoffeeService implements CoffeeServiceService<Context> {
    //  set up a map for storing order information
    orders = new Map<string, OrderData>();
    // set up an orders queue for handling orders 
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
                    description: "A clean-bodied, rounder, and more simplistic flavour profile.\nOften praised for mellow and less intense notes.\nFar less concentrated than espresso."
                },
                {
                    type: CoffeeType.POUR_OVER,
                    description: "Similar to drip coffee, but with a process that brings out more subtle nuances in flavor.\nMore concentrated than drip, but less than espresso."
                },
                {
                    type: CoffeeType.LATTE,
                    description: "A creamier, milk-based drink made with espresso.\nA subtle coffee taste, with smooth texture.\nHigh milk-to-coffee ratio."
                },
                {
                    type: CoffeeType.ESPRESSO,
                    description: "A highly concentrated form of coffee, brewed under high pressure.\nSyrupy, thick liquid in a small serving size.\n Firm, full-bodies, and intensly aromatic."
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

    // handle orders as they come in (FIFO), marking them completed based on some random timing
    handleOrders = async () => {
        console.log("handling orders...")
        while (true) {
            await new Promise(resolve => setTimeout(resolve, Math.random() * 5000 + 1000));
            let order = this.queue.shift()
            if (order != null) {
                order.status = OrderStatus.COMPLETED
                console.log(`order ${order.orderId} is completed.`)  
            }
        }
    }
}

// an empty context object
interface Context {}

// a data object to hold some order data
interface OrderData {
    orderId: string
    coffeeType: CoffeeType;
    status: OrderStatus;
}
