$version: "2"

namespace smithy.example

use aws.protocols#restJson1
use aws.protocols#restXml
use smithy.api#httpBasicAuth
use smithy.protocols#rpcv2Cbor

/// Directory of people and their profiles
@restJson1
@rpcv2Cbor
@restXml
@httpApiKeyAuth(name: "X-API-Key", in: "header", scheme: "Bearer")
@paginated(inputToken: "nextToken", outputToken: "nextToken", pageSize: "pageSize")
@externalDocumentation(Homepage: "https://example.com/people", "API Reference": "https://example.com/people/api")
@deprecated(since: "2023-01-01", message: "Use PeopleDirectoryV2 instead")
@unstable
@since("2022-01-01")
@title("People Directory Service")
@xmlNamespace(prefix: "pd", uri: "https://example.com/people")
@httpBasicAuth
@auth([httpBasicAuth, httpApiKeyAuth])
service PeopleDirectory {
    version: "01-01-2040"
    resources: [
        Person
    ]
    operations: [
        ListPeople
    ]
    errors: [
        ValidationError
        ServerError
    ]
}

/// A person in the directory
@since("2022-06-01")
resource Person {
    identifiers: {
        name: String
    }
    properties: {
        favoriteColor: String
        age: Integer
        birthday: Birthday
        profile: Profile
    }
    put: PutPerson
    read: GetPerson
    resources: [
        PersonImage
    ]
}

/// Profile image for a person
resource PersonImage {
    identifiers: {
        name: String
    }
    read: GetPersonImage
    put: PutPersonImage
    delete: DeletePersonImage
}

/// Create or update a person
@idempotent
@http(method: "PUT", uri: "/people/{name}", code: 200)
@httpChecksumRequired
@requestCompression(
    encodings: ["gzip"]
)
@optionalAuth
@endpoint(hostPrefix: "{name}.")
@since("2022-03-15")
@examples([
    {
        title: "Create person with profile",
        input: {
            name: "user",
            favoriteColor: "blue",
            age: 30,
            birthday: "1994-01-15T10:30:00Z",
            profile: {
                completenessScore: 85,
                displayName: "Test User",
                skills: ["Python", "AWS", "Docker"],
                socialProfiles: {
                    "linkedin": {
                        url: "https://linkedin.com/in/example",
                        lastUpdated: 1640995200,
                        followerCount: 250
                    }
                },
                employmentStatus: {
                    employed: {
                        company: "Example Corp",
                        jobTitle: "Engineer"
                    }
                }
            }
        },
        output: {
            name: "testuser",
            favoriteColor: "blue",
            age: 30,
            birthday: "1994-01-15T10:30:00Z",
            profile: {
                completenessScore: 85,
                displayName: "Test User",
                skills: ["Python", "AWS", "Docker"],
                employmentStatus: {
                    employed: {
                        company: "Example Corp",
                        jobTitle: "Engineer"
                    }
                }
            }
        }
    },
    {
        title: "Invalid name format",
        documentation: "Name must contain only letters and be at most 7 characters",
        input: {
            name: "user123",
            favoriteColor: "red",
            age: 25
        },
        allowConstraintErrors: true
        error: {
            shapeId: ValidationError,
            content: {
                message: "Invalid name format. Name must contain only letters.",
                resourceId: "user123"
            }
        }
    }
])
operation PutPerson {
    input := for Person {
        @httpLabel
        @required
        @length(max: 7)
        @hostLabel
        @pattern("^[a-zA-Z]+$")
        $name

        @httpQuery("favoriteColor")
        $favoriteColor

        @jsonName("Age")
        @range(max: 150)
        $age = 0

        @timestampFormat("date-time")
        $birthday

        @notProperty
        binary: Binary

        @notProperty
        @httpQueryParams
        queryParams: MapListString

        @notProperty
        @httpPrefixHeaders("X-Custom-")
        headers: StringMap

        @notProperty
        @recommended
        @internal
        metadata: String

        @notProperty
        @clientOptional
        notes: String

        $profile
    }

    output := for Person {
        @required
        $name

        @httpHeader("X-Favorite-Color")
        $favoriteColor

        @jsonName("Age")
        $age = 1

        $birthday

        @notProperty
        profile: Profile
    }
}

/// Get person details
@readonly
@http(method: "GET", uri: "/people/{name}", code: 200)
operation GetPerson {
    input := for Person {
        @httpLabel
        @required
        $name
    }

    output := for Person {
        @required
        $name

        $favoriteColor

        $age

        $birthday
    }
}

/// List all people
@readonly
@http(method: "GET", uri: "/people", code: 200)
@paginated(inputToken: "nextToken", outputToken: "nextToken", pageSize: "pageSize", items: "people")
operation ListPeople {
    input := {
        @httpQuery("nextToken")
        nextToken: String

        @httpQuery("pageSize")
        @range(min: 1, max: 100)
        pageSize: Integer
    }

    output := {
        @required
        people: PersonList

        nextToken: String
    }
}

/// Get a person's image
@readonly
@http(method: "GET", uri: "/people/{name}/image", code: 200)
@auth([])
operation GetPersonImage {
    input := for PersonImage {
        @required
        @httpLabel
        $name
    }

    output := for PersonImage {
        @required
        @httpHeader("Person-Name")
        $name

        @required
        @httpPayload
        image: Stream
    }
}

/// Upload a person's image
@idempotent
@http(method: "PUT", uri: "/people/{name}/images", code: 200)
operation PutPersonImage {
    input := for PersonImage {
        @required
        @httpLabel
        $name

        @httpHeader("Tags")
        tags: ListOfString

        @httpQuery("MoreTags")
        moreTags: ListOfString

        @required
        @httpPayload
        image: Stream
    }
}

/// Delete a person's image
@idempotent
@http(method: "DELETE", uri: "/people/{name}/image", code: 204)
operation DeletePersonImage {
    input := for PersonImage {
        @required
        @httpLabel
        $name
    }
}

/// Client validation error
@error("client")
@httpError(403)
@retryable(throttling: true)
structure ValidationError {
    @required
    message: String

    resourceId: String
}

/// Server error
@error("server")
@httpError(500)
structure ServerError {
    @required
    message: String

    @clientOptional
    details: String
}

/// Person's birthday
@sensitive
timestamp Birthday

@mediaType("application/octet-stream")
blob Binary

/// Image data stream
@streaming
blob Stream

list PersonList {
    member: PersonSummary
}

@references([
    {
        resource: Person
    }
    {
        resource: PersonImage
    }
])
structure PersonSummary {
    @required
    name: String

    favoriteColor: String

    age: Integer

    @xmlAttribute
    @xmlName("active")
    isActive: Boolean
}

/// Person's extended profile
@since("2023-02-01")
structure Profile {
    @required
    @range(min: 0, max: 100)
    completenessScore: Integer

    @clientOptional
    @xmlName("display-name")
    @since("2023-05-01")
    displayName: String

    @xmlFlattened
    @xmlName("skills")
    skills: SkillsList

    socialProfiles: SocialProfilesMap

    employmentStatus: EmploymentStatus
}

/// List of skills
@sparse
list SkillsList {
    @pattern("^[A-Za-z0-9 ]+$")
    @length(min: 1, max: 50)
    member: String
}

/// Social media profiles
@sparse
map SocialProfilesMap {
    @pattern("^[a-z]+$")
    key: String

    @xmlName("profile-info")
    value: SocialProfile
}

/// Social media profile info
structure SocialProfile {
    @required
    @pattern("^https?://.*$")
    url: String

    @timestampFormat("epoch-seconds")
    lastUpdated: Timestamp

    @range(min: 0, max: 1000000)
    followerCount: Integer
}

/// Employment status
@since("2023-08-01")
union EmploymentStatus {
    @xmlName("employed")
    employed: EmployedStatus

    @xmlName("self-employed")
    selfEmployed: SelfEmployedStatus

    @xmlName("unemployed")
    unemployed: UnemployedStatus

    @xmlName("retired")
    retired: RetiredStatus
}

structure EmployedStatus {
    @required
    @length(min: 1, max: 200)
    company: String

    @pattern("^[A-Za-z ]+$")
    jobTitle: String
}

structure SelfEmployedStatus {
    @required
    @length(min: 1, max: 200)
    businessName: String

    @pattern("^[A-Za-z ]+$")
    industry: String
}

structure UnemployedStatus {
    @timestampFormat("date-time")
    since: Timestamp
}

structure RetiredStatus {
    @timestampFormat("date-time")
    since: Timestamp

    @length(max: 200)
    previousEmployer: String
}

map StringMap {
    key: String
    value: String
}

map MapListString {
    key: String
    value: ListOfString
}

@uniqueItems
list ListOfString {
    @length(min: 1)
    member: String
}
