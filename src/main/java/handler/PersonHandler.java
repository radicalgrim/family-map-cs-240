package handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.PersonService;
import service.result.PersonResult;

import java.io.IOException;
import java.net.HttpURLConnection;

public class PersonHandler extends RequestHandler implements HttpHandler {
  PersonService service;
  PersonResult result;

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    try {
      if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
        Headers reqHeaders = exchange.getRequestHeaders();
        if (reqHeaders.containsKey("Authorization")) {

          String authToken = reqHeaders.getFirst("Authorization");
          String personId = decodePersonInfo(exchange);
          service = new PersonService();
          result = service.person(personId, authToken);
          if (result.getSuccess()) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
          }
          else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
          }
        }
        else {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
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

  private String decodePersonInfo(HttpExchange exchange) {
    String urlPath = exchange.getRequestURI().toString();
    urlPath = urlPath.substring(7);
    String personId = null;
    if (urlPath.contains("/")) {
      personId = urlPath.substring(urlPath.indexOf('/') + 1);
    }
    return personId;
  }

}
