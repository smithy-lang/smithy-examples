$version: "2"

namespace smithy.example

// aws.protocols#restJson1 is defined in the `smithy-aws-traits`
// package which is not in the classpath. An error with a hint
// pointing to this package should be emitted.
use aws.protocols#restJson1

@restJson1
service Weather {
    version: "2006-03-01"
    operations: [GetCurrentTemperature]
}

operation GetCurrentTemperature {
    input: GetCurrentTemperatureRequest
    output: GetCurrentTemperatureResponse
}

structure GetCurrentTemperatureRequest {
    latitude: Float
    longitude: Float
}

structure GetCurrentTemperatureResponse {
    feelsLike: Float
}
