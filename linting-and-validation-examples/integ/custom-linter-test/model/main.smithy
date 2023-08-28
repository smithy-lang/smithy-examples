$version: "2"

metadata validators = [
    {
        name: "ForbiddenDocumentation"
        severity: "WARNING"
        configuration: {
            forbid: ["meow"]
        }
    }
]

namespace example

// This will fail the `ForbiddedDocumentation` validator
@documentation("meow is your time!")
string BadAbbreviationShapeID
