$version: "2.0"

namespace smithy.example

// "error" is a trait that is used to specialize
// a structure as an error.
@error("client")
@httpError(404)
structure NoSuchResource {
    @required
    resourceType: String
}

@error("client")
@retryable
@httpError(429)
structure ThrottlingError {
    @required
    message: String
}

@error("server")
@httpError(500)
structure ServiceError {
    @required
    message: String
}
