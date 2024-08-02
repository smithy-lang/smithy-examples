$version: "2.0"

namespace example.traits

/// A basic annotation Trait.
@trait(selector: "structure")
structure annotation {}

@tags(["not-generated"])
@trait(selector: "member")
string url
