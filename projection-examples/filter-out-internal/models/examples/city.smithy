$version: "2.0"

namespace smithy.example

// In Smithy, example values of input structure members and the
// corresponding output or error structure members for an operation
// are grouped together into one set of example values for an operation.
apply GetCity @examples(
    [
        {
            title: "Get City Example"
            documentation: "Gets a city with the ID 1234"
            input: {cityId: "1234"}
            output: {
                name: "Seattle"
                coordinates: {latitude: 47.6, longitude: 122.3}
            }
        }
    ]
)

apply CreateCity @examples(
    [
        {
            title: "Create City Example"
            documentation: "An example that creates a City Called Seattle"
            input: {
                name: "Seattle"
                coordinates: {latitude: 47.6, longitude: 122.3}
            }
            output: {
                cityId: "1234"
                name: "Seattle"
                coordinates: {latitude: 47.6, longitude: 122.3}
            }
        }
    ]
)
