package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.LoginService;
import service.request.LoginRequest;
import service.result.LoginResult;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class LoginHandler extends PostHandler implements HttpHandler {
  LoginService service;
  LoginRequest request;
  LoginResult result;

  public LoginHandler() {
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    try {
      if (exchange.getRequestMethod().toUpperCase().equals("POST")) {

        InputStream reqBody = exchange.getRequestBody();
        String reqData = readString(reqBody);
        request = JsonSerializer.deserialize(reqData, LoginRequest.class);
        service = new LoginService();
        result = service.login(request);
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

