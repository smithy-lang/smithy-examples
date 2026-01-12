$version: "2"

namespace com.example

use aws.api#service
use aws.protocols#restJson1
use smithy.protocols#rpcv2Cbor

/// Allows users to retrieve a menu, create a coffee order, and
/// and to view the status of their orders
@title("Coffee Shop Service")
@restJson1
@rpcv2Cbor
@service(sdkId: "CoffeeShop")
service CoffeeShop {
    version: "2024-08-23"
    operations: [
        GetMenu
    ]
    resources: [
        Order
    ]
}

/// Retrieve the menu
@http(method: "GET", uri: "/menu")
@readonly
operation GetMenu {
    output := {
        items: CoffeeItems
    }
}
