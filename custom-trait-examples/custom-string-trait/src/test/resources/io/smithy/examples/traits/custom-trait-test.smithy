$version: "2.0"

namespace io.smithy.example.test

use io.smithy.example#jsonName

structure TestStructure {
    @jsonName("TESTING")
    testMember: String
}

