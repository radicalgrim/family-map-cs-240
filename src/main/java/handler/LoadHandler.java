package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.LoadService;
import service.request.LoadRequest;
import service.result.LoadResult;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class LoadHandler extends PostHandler implements HttpHandler {
  LoadService service;
  LoadRequest request;
  LoadResult result;

  public LoadHandler() {
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {

    try {

      if (exchange.getRequestMethod().toUpperCase().equals("POST")) {

        // Extract the JSON string from the HTTP request body
        InputStream reqBody = exchange.getRequestBody();
        String reqData = readString(reqBody);
        request = JsonSerializer.deserialize(reqData, LoadRequest.class);
        service = new LoadService();
        result = service.load(request);
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
      }
      else {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
      }

      // Send a response body
      OutputStream respBody = exchange.getResponseBody();
      String respData = JsonSerializer.serialize(result);
      writeString(respData, respBody);
      respBody.close();

    } catch (IOException e) {
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
      exchange.getResponseBody().close();
      e.printStackTrace();
    }
  }
}

/*
/load
        URL Path: /load
        Description: Clears all data from the database (just like the /clear API), and then loads the posted user, person, and event data into the database.
        HTTP Method: POST
        Auth Token Required: No

        Request Body: The “users” property in the request body contains an array of users to be created. The “persons” and “events” properties contain family history information for these users.  The objects contained in the “persons” and “events” arrays should be added to the server’s database.  The objects in the “users” array have the same format as those passed to the /user/register API with the addition of the personID.  The objects in the “persons” array have the same format as those returned by the /person/[personID] API.  The objects in the “events” array have the same format as those returned by the /event/[eventID] API.
        {
        “users”: [  // Array of User objects ],
        “persons”: [  // Array of Person objects  ],
        “events”: [  // Array of Event objects  ]
        }
        Errors: Invalid request data (missing values, invalid values, etc.), Internal server error

        Success Response Body:
        {
        “message”: “Successfully added X users, Y persons, and Z events to the database.”
        “success”:”true”		// Boolean identifier
        }
        Error Response Body:
        {
        “message”: “Description of the error”
        “success”:”false”		// Boolean identifier
        }
*/
