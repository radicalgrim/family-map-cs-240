package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.ClearService;
import service.result.ClearResult;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class ClearHandler extends RequestHandler implements HttpHandler {
  ClearService service;
  // ClearRequest request;
  ClearResult result;

  public ClearHandler() {
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {

    try {

      if (exchange.getRequestMethod().toUpperCase().equals("POST")) {

        // TODO: Call the clear service
        // Normally would send in a request body here
        service = new ClearService();
        result = service.clear();


        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
      }
      else {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
      }


      // TODO: Send a response body
      OutputStream respBody = exchange.getResponseBody();
      String respData = result.toString(); // TODO: Fix toString method
      writeString(respData, respBody);
      respBody.close();



    } catch (IOException e) {
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
      exchange.getResponseBody().close();
      e.printStackTrace();
    }
  }
}