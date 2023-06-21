$version: "2.0"

namespace io.smithy.example.test

use io.smithy.example#resourceMetadata

@resourceMetadata(
    description: "Description",
    type: "SPECIAL"
    associatedStructures: [ AssociatedStruct ]
)
@documentation("I should trigger an error validation event")
resource TestResource {
    identifiers: { id: String }
}

structure AssociatedStruct {}
