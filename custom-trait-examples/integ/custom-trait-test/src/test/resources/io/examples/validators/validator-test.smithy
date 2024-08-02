$version: "2.0"

namespace example.test

use example.traits#jsonName

structure TestStructure {
    @jsonName("TESTING")
    testMember: Blob
}
