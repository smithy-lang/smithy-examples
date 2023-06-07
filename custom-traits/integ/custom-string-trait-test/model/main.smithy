$version: "1.0"

namespace example

use io.smithy.example#jsonName

// This will fail the `Documentation` validator
structure MyStructure {
    @jsonName("TESTING")
    myMember: String
}

