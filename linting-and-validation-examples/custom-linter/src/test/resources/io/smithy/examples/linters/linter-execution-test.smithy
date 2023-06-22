$version: "2.0"

metadata validators = [
    {
        name: "ForbiddenDocumentation"
        severity: "DANGER"
        configuration: {
            forbid: ["TODO"]
        }
    }
]

namespace smithy.example.test

// This should fail the `ForbiddedDocumentation` validator
@documentation("TODO write the documentation")
string BadAbbreviationShapeID
