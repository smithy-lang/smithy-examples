$version: "2.0"

namespace io.smithy.example.test

use io.smithy.example#resourceMetadata

@resourceMetadata(
    description: "Description",
    type: "SPECIAL"
    associatedStructures: [ AssociatedStruct ]
)
resource TestResource {
    identifiers: { id: String }
}

structure AssociatedStruct {}
