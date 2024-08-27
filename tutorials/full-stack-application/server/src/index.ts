import { getCoffeeShopServiceHandler } from "@com.example/coffee-service-server";
import { IncomingMessage, ServerResponse, createServer } from "http";
import { convertRequest, writeResponse } from "@aws-smithy/server-node";
import { CoffeeShop } from "./CoffeeShop";


// Instantiate our coffee service implementation
const coffeeService = new CoffeeShop();
// Create a service handler using our coffee service
const serviceHandler = getCoffeeShopServiceHandler(coffeeService);
// The coffee shop context object
const ctx = { orders: new Map(), queue: [] };

// Create the node server with the service handler
const server = createServer(async function (
  req: IncomingMessage,
  res: ServerResponse<IncomingMessage> & { req: IncomingMessage }
) {
  // Convert NodeJS's http request to an HttpRequest.
  const httpRequest = convertRequest(req);

  // Call the service handler, which will route the request to the GreetingService
  // implementation and then serialize the response to an HttpResponse.
  const httpResponse = await serviceHandler.handle(httpRequest, ctx);

  // Write the HttpResponse to NodeJS http's response expected format.
  return writeResponse(httpResponse, res);
});

const port = 3001
server.listen(port);
console.log(`Started server on port ${port}...`);

// Asynchronously handle orders as they come in
coffeeService.handleOrders(ctx)
