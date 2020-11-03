package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.file.Files;

public class FileHandler implements HttpHandler {

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    try {
      if (exchange.getRequestMethod().toUpperCase().equals("GET")) {

        File file = getFile(exchange);
        OutputStream respBody = exchange.getResponseBody();
        Files.copy(file.toPath(), respBody);
        respBody.close();

      } else {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
      }

    } catch (IOException e) {
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
      exchange.getResponseBody().close();
      e.printStackTrace();
    }
  }

  private File getFile(HttpExchange exchange) throws IOException {
    String urlPath = exchange.getRequestURI().toString();
    if (urlPath == null || urlPath.equals("/")) {
      urlPath = "/index.html";
    }
    String filePath = "web" + urlPath;
    File file = new File(filePath);
    if (!file.exists()) {
      filePath = "web/HTML/404.html";
      file = new File(filePath);
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
    }
    else {
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
    }
    return file;
  }
}
