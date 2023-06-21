$version: "2.0"

namespace smithy.example


@paginated(inputToken: "nextToken", outputToken: "nextToken",
           pageSize: "pageSize")
/// Provides weather forecasts
@title("Weather Service")
service Weather {
    version: "2006-03-01",
    resources: [City],
    operations: [GetCurrentTime]
    // Add common errors that could be thrown by any route in the service
    errors: [ServiceError, ThrottlingError]
}

@readonly
operation GetCurrentTime {
    output: GetCurrentTimeOutput
}

structure GetCurrentTimeOutput {
    @required
    time: Timestamp
}



