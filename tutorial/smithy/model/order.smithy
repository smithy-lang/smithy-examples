$version: "2.0"

namespace com.example

/// An Order resource, which has an id and descibes an order by the type of coffee
/// and the order's status
resource Order {
    identifiers: {
        id: Uuid,
    },
   properties: {
       coffeeType: CoffeeType
       status: OrderStatus
   }
   read:   GetOrder,
   create: CreateOrder
}

/// Create an order
@idempotent
@http(method: "PUT", uri: "/order")
operation CreateOrder {
   input := for Order {
        @required
        $coffeeType
   }
   output := for Order {
        @required 
        $id 
        
        @required
        $coffeeType
        
        @required
        $status
   }
}

/// Retrieve an order
@readonly
@http(method: "GET", uri: "/order/{id}")
operation GetOrder {
   input := for Order {
        @httpLabel
        @required
        $id
   }
   output := for Order {
        @required 
        $id 
        
        @required
        $coffeeType 
        
        @required
        $status
   }
   errors: [
        OrderNotFound
   ]
}

/// An error indicating that an order could not be found
@httpError(404)
@error("client")
structure OrderNotFound {
   message: String
   orderId: Uuid
}

/// An identifier to describe a unique order
@length(min: 1, max: 128)
@pattern("^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$")
string Uuid


/// An enum describing the status of an order
enum OrderStatus {
   IN_PROGRESS, 
   COMPLETED
}