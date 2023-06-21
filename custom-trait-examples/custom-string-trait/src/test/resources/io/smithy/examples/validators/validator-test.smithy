$version: "2.0"

namespace io.smithy.example.test

structure TestStructure {
    @io.smithy.example#jsonName("TESTING")
    @smithy.api#jsonName("OOPS! DUPLICATION!")
    testMember: String
}
