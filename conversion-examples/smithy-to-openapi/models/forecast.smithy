$version: "2.0"

namespace smithy.example

resource Forecast {
    identifiers: {cityId: CityId}
    read: GetForecast
}

@readonly
@http(method: "GET", uri: "/city/{cityId}/forecast")
operation GetForecast {
    input := for Forecast {
        @required
        @httpLabel
        $cityId
    }

    output := {
        rating: Rating
        forecast: ForecastResult
    }
}

/// How happy do we expect this forecast to make you
enum Rating {
    SUPER_HAPPY
    SOMEWHAT_HAPPY
    MEH
    NOT_SO_HAPPY
    UNHAPPY
}

// Unions represent a `one-of` relationship. The forecast could be
// either `rain` with a float value or `sun` with an integer value
union ForecastResult {
    rain: ChanceOfRain
    sun: UVIndex
}

float ChanceOfRain

// Range is a trait that constrains the value of a number
@range(min: 1, max: 15)
integer UVIndex
