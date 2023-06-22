$version: "1.0"

namespace example

// This will fail the `AbbreviationName` linter
string BadAbbreviationShapeID

// This will fail the `CamelCase` linter
string shouldbecamelcase

// Fails the `MissingSensitiveTrait` linter
string Ssn


operation MyOperation {
    input: BadlyNamedOperationInput
    errors: [BadlyName404]
}

// This will fail the `OperationInputName` validator
structure BadlyNamedOperationInput {}

// Fails the `OperationErrorName` validator
@error("client")
structure BadlyName404 {}

// Fails the `RawIntegerWithoutRange` validator
integer MyInt
