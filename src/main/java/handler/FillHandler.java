package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.FillService;
import service.result.FillResult;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class FillHandler extends RequestHandler implements HttpHandler {
  FillService service;
  FillResult result;

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    try {
      if (exchange.getRequestMethod().toUpperCase().equals("POST")) {

        String[] fillInfo = decodeFillInfo(exchange);
        service = new FillService();
        result = service.fill(fillInfo[0], fillInfo[1]);

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
      }
      else {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
      }

      makeResponseBody(exchange, result);

    } catch (IOException e) {
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
      exchange.getResponseBody().close();
      e.printStackTrace();
    }
  }

  private String[] decodeFillInfo(HttpExchange exchange) {
    String urlPath = exchange.getRequestURI().toString();
    urlPath = urlPath.substring(6);
    String username;
    String generations;
    if (urlPath.contains("/")) {
      username = urlPath.substring(0, urlPath.indexOf('/'));
      generations = urlPath.substring(urlPath.indexOf('/') + 1);
    }
    else {
      username = urlPath;
      generations = "4";
    }
    return new String[]{username, generations};
  }

}

/*
/fill/[username]/{generations}
URL Path: /fill/[username]/{generations}
Example: /fill/susan/3
Description: Populates the server's database with generated data for the specified user
name. The required "username" parameter must be a user already registered with the
server. If there is any data in the database already associated with the given user
name, it is deleted. The optional “generations” parameter lets the caller specify the
number of generations of ancestors to be generated, and must be a non-negative integer
(the default is 4, which results in 31 new persons each with associated events).
HTTP Method: POST
Auth Token Required: No
Request Body: None
Errors: Invalid username or generations parameter, Internal server error
Success Response Body:
{
	“message”: “Successfully added X persons and Y events to the database.”
“success”:”true”		// Boolean identifier
}
Error Response Body:
{
	“message”: “Description of the error”
“success”:”false”		// Boolean identifier
}

 */
