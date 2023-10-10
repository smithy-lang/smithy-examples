$version: "2.0"

namespace smithy.example

resource City {
    identifiers: {cityId: CityId}
    properties: {coordinates: CityCoordinates, name: String}
    read: GetCity
    create: CreateCity
    resources: [Forecast]
}

@readonly
@http(method: "GET", uri: "/city/{cityId}")
operation GetCity {
    input := for City {
        @required
        @httpLabel
        $cityId
    }

    output := for City {
        // "required" is used on output to indicate if the service
        // will always provide a value for the member.
        @required
        $name

        @required
        $coordinates
    }

    errors: [
        NoSuchResource
    ]
}

@http(method: "POST", uri: "/city")
operation CreateCity {
    input := for City {
        @required
        $name

        @required
        $coordinates
    }

    output := for City {
        @required
        $cityId

        @required
        $name

        @required
        $coordinates
    }
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
