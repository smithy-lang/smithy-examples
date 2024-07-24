$version: "2.0"

namespace example.test

use example.traits#annotationTrait
use example.traits#jsonName

@annotationTrait
structure MyStructure {
    myMember: String
}

structure MyStructure {
    @jsonName("TESTING")
    myMember: String
}
