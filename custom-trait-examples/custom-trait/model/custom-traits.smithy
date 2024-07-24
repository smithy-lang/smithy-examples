$version: "2.0"

namespace example.traits

/// A basic annotation Trait
@trait(selector: "structure")
structure annotationTrait {}

/// A string trait
/// Define trait validators to apply trait validation directly in the model
/// see: https://smithy.io/2.0/spec/model-validation.html#smithy-api-traitvalidators-trait
@traitValidators(
    "jsonName.conflict": {
        selector: "member :test(> blob)"
        message: "This shape cannot be applied to members that target blobs."
    }
)
@trait(selector: "member")
string jsonName
