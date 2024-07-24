$version: "2.0"

namespace example.test

use example.traits#resourceMetadata

@resourceMetadata(
    description: "Description",
    type: "SPECIAL"
    associatedStructures: [ AssociatedStruct ]
)
resource TestResource {
    identifiers: { id: String }
}

structure AssociatedStruct {}
