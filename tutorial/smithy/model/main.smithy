$version: "2.0"

namespace com.example

use aws.protocols#restJson1
use smithy.framework#ValidationException

/// Allows users to create a coffee order and
/// and to view the status of their orders
@title("Coffee Service")
@restJson1
service CoffeeService {
    version: "2024-04-04"
    operations: [
        GetMenu
    ]
    resources: [
        Order
    ]
    errors: [
        ValidationException
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
