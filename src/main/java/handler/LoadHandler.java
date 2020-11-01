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
