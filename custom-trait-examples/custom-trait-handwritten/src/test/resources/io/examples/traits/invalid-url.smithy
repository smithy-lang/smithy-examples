$version: "2.0"

namespace example.test

use example.traits#url
use example.traits#annotation

@annotation
structure TestStructure {
    @url("badUrl:bad")
    member: String
}
