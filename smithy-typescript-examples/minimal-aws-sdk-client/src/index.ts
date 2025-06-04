import { DynamoDB }
    from '@aws-sdk/client-dynamodb'

// Create the client, specifying the exact endpoint to use
const client = new DynamoDB({
    endpoint: {
        protocol: 'https',
        hostname: 'dynamodb.us-west-2.amazonaws.com',
        port: 443,
        path: '/'
    },
    region: 'us-west-2'
})

async function main() {
    console.log("\n\nDOOT DOOT.\n\n")

    // Call `ListTables`, we assume you have a testing table in your account called 'MyTestTable'
    let listTablesResponse = await client.listTables({})
    // Log the response, there should be a list of tables under `TableName` (which includes 'MyTestTable')
    console.log("ListTablesCommandOutput:", listTablesResponse)

    // Call `PutItem` with the table (the table is the same table from above)
    let putItemResponse = await client.putItem({
        TableName: 'MyTestTable',
        Item: {
            'Id': { S: '0' },
            'Name': { S: 'abc123' }
        }
    })
    // Log the response, there should a '200' in `$metadata.httpStatusCode`
    console.log("PutItemCommandOutput:", putItemResponse)

    // Call `GetItem` to get the item we created above
    let getItemResponse = await client.getItem({
        TableName: 'MyTestTable',
        Key: {
            'Id': { S: '0' }
        }
    })
    // Log the response, we should see the item under the `Item` field
    console.log("GetItemCommandOutput:", getItemResponse)

}

main().catch(error => console.error("Unhandled error:", error));
