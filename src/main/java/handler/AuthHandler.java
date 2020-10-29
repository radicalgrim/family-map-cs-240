package handler;

public abstract class AuthHandler extends RequestHandler {
  /*
  if (reqHeaders.containsKey("Authorization")) {
    String authToken = reqHeaders.getFirst("Authorization");
    if (authToken.equals("afj232hj2332")) {

      // Extract the JSON string from the HTTP request body

      // Get the request body input stream
      InputStream reqBody = exchange.getRequestBody();

      // Read JSON string from the input stream
      String reqData = readString(reqBody);

      // Display/log the request JSON data
      System.out.println(reqData);


      // Claim a route based on the request data


      // Start sending the HTTP response to the client, starting with
      // the status code and any defined headers.
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

    } else {
      // The auth token was invalid somehow, so we return a "not authorized"
      // status code to the client.
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
    }
  } else {
    // We did not get an auth token, so we return a "not authorized"
    // status code to the client.
    exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
  }
  */
}
