package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.RegisterService;
import request.RegisterRequest;
import result.RegisterResult;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class RegisterHandler extends PostHandler implements HttpHandler {
  RegisterService service;
  RegisterRequest request;
  RegisterResult result;

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    try {
      if (exchange.getRequestMethod().toUpperCase().equals("POST")) {

        InputStream reqBody = exchange.getRequestBody();
        String reqData = readString(reqBody);
        request = JsonSerializer.deserialize(reqData, RegisterRequest.class);
        service = new RegisterService();
        result = service.register(request);
        if (result.getSuccess()) {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        }
        else {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        }

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

}
