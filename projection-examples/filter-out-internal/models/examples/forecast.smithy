$version: "2.0"

namespace smithy.example

apply GetForecast @examples(
    [
        {
            title: "Create City Example"
            documentation: "An example that creates a City Called Seattle"
            input: {cityId: "1234"}
            output: {
                rating: "SOMEWHAT_HAPPY"
                forecast: {rain: 12.0}
            }
        }
    ]
)
