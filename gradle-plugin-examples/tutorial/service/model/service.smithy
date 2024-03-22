$version: "2.0"

metadata validators = [
    {
        // The name of our custom validator
        name: "DocUrl"
    }
]

namespace com.example.cafe.service

// Add common shapes from `lib` subproject
use com.example.cafe.common#MenuItemId

use com.example.cafe.common#Price
use com.example.cafe.common#Style
use com.example.cafe.common#Type

/// Provides an API for ordering coffee
service DrinkService {
    version: "2024-03-30"
    resources: [
        Order
    ]
}

@externalDocumentation(wikipedia: "https://en.wikipedia.org/wiki/Order")
resource Order {
    identifiers: { id: MenuItemId }
    properties: { price: Price, style: Style, type: Type }
    create: CreateOrder
    read: GetOrder
}

operation CreateOrder {
    input := for Order {
        $style
        $type
    }

    output := for Order {
        @required
        $id
    }
}

@readonly
operation GetOrder {
    input := for Order {
        @required
        $id
    }

    output := for Order {
        $price
        $style
        $type
    }
}
