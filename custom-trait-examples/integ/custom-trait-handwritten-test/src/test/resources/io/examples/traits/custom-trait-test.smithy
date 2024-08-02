$version: "2.0"

namespace example.test

use example.traits#url

structure TestStruct {
    @url("https://example.com/")
    member: String
}
