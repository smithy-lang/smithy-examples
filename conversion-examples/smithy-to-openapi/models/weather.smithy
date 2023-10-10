$version: "2.0"

namespace smithy.example

use aws.protocols#restJson1

/// Provides weather forecasts
@paginated(inputToken: "nextToken", outputToken: "nextToken", pageSize: "pageSize")
@restJson1
@title("Weather Service")
service Weather {
    version: "2006-03-01"
    resources: [City]
    operations: [GetCurrentTime]
    // Add common errors that could be thrown by any route in the service
    errors: [ServiceError, ThrottlingError]
}

@readonly
@http(method: "GET", uri: "/current-time")
operation GetCurrentTime {
    output := {
        @required
        time: Timestamp
    }
}
