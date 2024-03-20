$version: "2.0"

namespace com.example.cafe.common

/// The price of a menu item should be a positive number
@range(min: 0.0)
bigDecimal Price

enum Style {
    ICED
    HOT
}

enum Type {
    COFFEE
    TEA
    COCOA
}

/// ID used to Identify a menu item
@length(min: 1, max: 64)
@pattern("^[a-zA-Z0-9_]$")
string MenuItemId
