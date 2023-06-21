$version: "2.0"

namespace smithy.example

resource City {
    identifiers: { cityId: CityId }
    properties: {
        coordinates: CityCoordinates
        name: String
    }
    read: GetCity
    create: CreateCity
    resources: [Forecast]
}

@readonly
operation GetCity {
    input: GetCityInput,
    output: GetCityOutput,
    errors: [NoSuchResource]
}

@input
structure GetCityInput for City {
    // "cityId" provides the identifier for the resource and
    // has to be marked as required.
    @required
    @httpLabel
    $cityId
}

structure GetCityOutput for City {
    // "required" is used on output to indicate if the service
    // will always provide a value for the member.
    @required
    $name

    @required
    $coordinates
}

operation CreateCity {
    input: CreateCityInput,
    output: CreateCityOutput
}

@input
structure CreateCityInput for City {
    @required
    $name

    @required
    $coordinates
}

@output
structure CreateCityOutput for City {
    @required
    $cityId

    @required
    $name

    @required
    $coordinates
}

// "pattern" is a trait that constrains the string value
@pattern("^[A-Za-z0-9 ]+$")
string CityId

structure CityCoordinates {
    @required
    latitude: Float

    @required
    longitude: Float
}


