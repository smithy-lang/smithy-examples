import { CoffeeItem, CoffeeShop, CoffeeType, OrderStatus } from "@com.example/coffee-service-client";

// Convert a string to a "friendly" variant
export function displayName(str: string): string {
    const tmp = str ? str.replaceAll("_", " ").toLowerCase() : ""
    return tmp.replace(/\b(\w)/g, s => s.toUpperCase());
}

// Get the right image for a given coffee type
export function getImage(type: CoffeeType | string): string {
    switch (type) {
        case CoffeeType.POUR_OVER:
            return "/pour-over.png"
        case CoffeeType.DRIP:
            return "/drip.png"
        case CoffeeType.LATTE:
                return "/latte.png"    
        case CoffeeType.ESPRESSO:
            return "/espresso.png"
        case CoffeeType.COLD_BREW:
            return "/cold-brew.png"
        default:
            return "/pour-over.png"
    }
}

export interface Order {
    coffeeType: CoffeeType;
    status: OrderStatus;
}

// create a coffee service client singleton and getter
let client: CoffeeShop
export function getClient(): CoffeeShop {
    return client || (client = new CoffeeShop({
        endpoint: {
            protocol: "http",
            hostname: "localhost",
            port: 3001,
            path: "/"
        }
    }));
}

// coffee service client helpers ------
export async function getMenuItems(): Promise<CoffeeItem[]> {
    let items: CoffeeItem[] = []
    try {
        const res = await getClient().getMenu();
        items = res.items || []
    } catch (err) {
        console.log(err)
    }
    return items
}

export async function submitOrder(cofeeType: CoffeeType): Promise<string> {
    let orderId: string = ""
    try {
        const res = await getClient().createOrder({
            coffeeType: cofeeType
        });
        orderId = res.id || ""
    } catch (err) {
        console.log(err)
    }
    return orderId
}

export async function getOrder(orderId: string): Promise<Order | undefined> {
    try {
        const res = await getClient().getOrder({
            id: orderId
        });
        if (res.coffeeType && res.status) {
            return {
                coffeeType: res.coffeeType,
                status: res.status
            }
        }
    } catch (err) {
        console.log(err)
    }
}
// ------------------------------------
