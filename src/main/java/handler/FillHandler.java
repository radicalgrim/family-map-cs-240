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
