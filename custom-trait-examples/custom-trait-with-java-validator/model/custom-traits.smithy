$version: "2.0"

namespace example.traits

@trait(
    selector: "resource"
    breakingChanges: [
        {
            change: "presence"
        }
    ]
)
structure resourceMetadata {
    /// Provides a custom name for your resource.
    @required
    description: String

    /// A type for the resource
    @required
    type: ResourceType

    /// A list of associated structures
    associatedStructures: StructureIdList
}

enum ResourceType {
    NORMAL
    SPECIAL
    NONE
}

@private
list StructureIdList {
    member: IdRefString
}

@idRef(failWhenMissing: true, selector: "structure")
string IdRefString
