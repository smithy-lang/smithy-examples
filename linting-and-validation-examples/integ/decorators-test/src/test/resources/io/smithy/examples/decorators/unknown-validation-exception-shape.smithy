$version: "2"

namespace smithy.example

// smithy.framework#ValidationException is defined in the
// `smithy-validation-model` package which is not in the classpath. An
// error with a hint pointing to this package should be emitted.
use smithy.framework#ValidationException

service Weather {
    version: "2006-03-01"
    operations: [GetCurrentTemperature]
}

operation GetCurrentTemperature {
    input: GetCurrentTemperatureRequest
    output: GetCurrentTemperatureResponse
    // smithy.framework#ValidationException is defined in the `smithy-validation-model` package
    errors: [ValidationException]
}

structure GetCurrentTemperatureRequest {
    latitude: Float
    longitude: Float
}

structure GetCurrentTemperatureResponse {
    feelsLike: Float
}
