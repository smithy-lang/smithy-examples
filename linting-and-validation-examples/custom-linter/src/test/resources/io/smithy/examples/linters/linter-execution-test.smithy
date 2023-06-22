$version: "2.0"

metadata validators = [
    {
        name: "ForbiddenDocumentation"
        severity: "DANGER"
        configuration: {
            forbid: ["TEST"]
        }
    }
]

namespace smithy.example.test

// This should fail the `ForbiddedDocumentation` validator
@documentation("BAD TEST BAD")
string BadAbbreviationShapeID
