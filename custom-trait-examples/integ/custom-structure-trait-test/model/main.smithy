$version: "2.0"

namespace example

use io.smithy.example#resourceMetadata

@resourceMetadata(
    description: "woo"
    type: "NORMAL"
    associatedStructures: [ForecastStruct]
)
resource Forecast {
    identifiers: {forecastId: ForecastId}
}

string ForecastId

structure ForecastStruct {}
