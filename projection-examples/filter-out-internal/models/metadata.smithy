$version: "2.0"

metadata authors = [ "Bob", "Alice"]
metadata company = "Examples R Us"
metadata license = "MYLICENSE 4.0"
metadata foo = "bar"
metadata validators = [
    {
        name: "EmitEachSelector"
        id: "OperationInputName"
        message: "This shape is referenced as input but the name does not end with 'Input'"
        configuration: {
            selector: "operation -[input]-> :not([id|name$=Input i])"
        }
    }
]
