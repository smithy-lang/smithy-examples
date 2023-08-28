$version: "2.0"

metadata validators = [
    {name: "AbbreviationName"}
    {name: "CamelCase"}
    {name: "NoninclusiveTerms"}
    {name: "RepeatedShapeName"}
    {name: "ShouldHaveUsedTimestamp"}
    // Require range for integers
    {
        name: "EmitEachSelector"
        id: "RawIntegerWithoutRange"
        configuration: {
            messageTemplate: """
                This number shape in member `@{id}` of the operation input `@{var|structure}` \
                does not have a range constraint on both its minimum or maximum value. \
                Add the `@@range` trait to this integer shape and provide both minimum and maximum values. \
                For example, `@@range(min: 1, max: 500)`.
                """
            selector: """
                operation -[input]-> $structure(*) > member
                :test(> number:not([trait|range|min]):not([trait|range|max]))
                """
        }
    }
    {
        name: "EmitEachSelector"
        id: "RawIntegerWithoutRangeMin"
        configuration: {
            messageTemplate: """
                This number shape in member `@{id}` of the operation input `@{var|structure}` \
                does not have a maximum range constraint. \
                Add a minimum value to the `@@range` trait on this shape. \
                For example, `@@range(>>> min: 1 <<<, max: 500)`.
                """
            selector: """
                operation -[input]-> $structure(*) > member
                :test(> number[trait|range]:not([trait|range|min]))
                """
        }
    }
    {
        name: "EmitEachSelector"
        id: "RawIntegerWithoutRangeMax"
        configuration: {
            messageTemplate: """
                This number shape in member `@{id}` of the operation input `@{var|structure}` \
                does not have a maximum range constraint. \
                Add a maximum value to the `@@range` trait on this shape. \
                For example, `@@range(min: 1, >>> max: 500 <<<)`.
                """
            selector: """
                operation -[input]-> $structure(*) > member
                :test(> number[trait|range]:not([trait|range|max]))
                """
        }
    }
    // Require limits on all lists
    {
        name: "EmitEachSelector"
        id: "ListWithoutLengthConstraint"
        configuration: {
            messageTemplate: """
                List shape `@{id}` does not have a length constraint specified. \
                Add the `@@length` trait to the list shape. For example, `@@length(min: 1, max: 2)`.
                """
            selector: "list:not([trait|length])"
        }
    }
    {
        name: "EmitEachSelector"
        id: "ListWithoutLengthConstraintMinimum"
        configuration: {
            messageTemplate: """
                List shape `@{id}` does not have a minimum length specified. \
                Add a `min` value to the `@@length` trait on the list shape. \
                For example, `@@length(>>> min: 1 <<<, max: 2)`.
                """
            selector: "list[trait|length]:not([trait|length|min])"
        }
    }
    {
        name: "EmitEachSelector"
        id: "ListWithoutLengthConstraintMaximum"
        configuration: {
            messageTemplate: """
                List shape `@{id}` does not have a maximum length specified. \
                Add a `max` value to the `@@length` trait on the list shape. \
                For example, `@@length(min: 1, >>> max: 2 <<<)`.
                """
            selector: "list[trait|length]:not([trait|length|max])"
        }
    }
    // Require strings to have a pattern constraint
    {
        name: "EmitEachSelector"
        id: "RawStringWithoutPattern"
        namespaces: ["example.common"]
        configuration: {
            messageTemplate: """
                This String shape in member `@{id}` of the operation input `@{var|structure}` \
                does not have a pattern constraint. \
                Add the `@@pattern` trait to this string shape and provide a regex pattern. \
                For example, `@@pattern("^[\\S\\s]+$")`.
                """
            selector: """
                operation -[input]-> $structure(*) > member
                :test(> string:not([trait|enum]):not([trait|pattern]))
                """
        }
    }
    // Limit shape name length
    {
        name: "EmitEachSelector"
        id: "ShapeNameLength"
        namespaces: ["example.common"]
        configuration: {
            messageTemplate: """
                Shape name @{id|name} is @{id|name|(length)} characters long.
                Shape names must be less than 60 characters and longer than 3 characters.
                """
            selector: ":not([@id|name: @{(length)} <= 60 && @{(length)} >= 3])"
        }
    }
]
