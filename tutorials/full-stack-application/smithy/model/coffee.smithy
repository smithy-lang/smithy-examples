$version: "2.0"

namespace com.example

/// An enum describing the types of coffees available
enum CoffeeType {
    DRIP
    POUR_OVER
    LATTE
    ESPRESSO
    COLD_BREW
}

/// A structure which defines a coffee item which can be ordered
structure CoffeeItem {
    @required
    type: CoffeeType

    @required
    description: String
}

/// A list of coffee items
list CoffeeItems {
    member: CoffeeItem
}
