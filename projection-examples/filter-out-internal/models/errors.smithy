$version: "2.0"

namespace smithy.example

// "error" is a trait that is used to specialize
// a structure as an error.
@error("client")
structure NoSuchResource {
    @required
    resourceType: String
}

@error("client")
@retryable
structure ThrottlingError {
    @required
    message: String
}

@error("server")
structure ServiceError {
    @required
    message: String
}
