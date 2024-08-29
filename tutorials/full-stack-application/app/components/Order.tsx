import { getOrder, submitOrder } from "@/app";
import { CoffeeType } from "@com.example/coffee-shop-client";
import OrderButton from "./OrderButton";
import { redirect } from "next/navigation";

export interface OrderProps {
    coffeeType: CoffeeType
  }


const Order = ({ coffeeType }: OrderProps) => {
    const handleOrderWithType = handleOrder.bind(null, coffeeType)
    return (
        <form action={handleOrderWithType}>
            <input name="coffeeType" className="hidden" onChange={handleOrderWithType} value={coffeeType}/>
            <OrderButton></OrderButton>
        </form>
  );
}

export default Order;

async function handleOrder(coffeeType: CoffeeType) {
    "use server";
    console.log(`handling order for: ${coffeeType}`)
    const id = await submitOrder(coffeeType)
    console.log(`order submitted with id: ${id}`)
    
    // Poll until the order is completed.
    console.log(`waiting for order: ${id}`)
    let isReady = false

    do {
        await new Promise(f => setTimeout(f, 1000))
        let order = await getOrder(id)
        console.log(`* order status: ${order?.status}`)
        if (order?.status == "COMPLETED") {
            isReady = true
            console.log("order is ready!")
            redirect(`/order?coffeeType=${order?.coffeeType}`)    
        }
    } while (!isReady)
};
