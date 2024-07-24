$version: "2.0"

namespace example.test

use example.traits#jsonName
use example.traits#annotationTrait

structure TestStructure {
    @jsonName("TESTING")
    testMember: String
}

@annotationTrait
structure TestSpecial {
    field: String
}

