package handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

public abstract class AuthHandler extends PostHandler {

  public Boolean authorize(HttpExchange exchange) {
    Headers reqHeaders = exchange.getRequestHeaders();
    if (reqHeaders.containsKey("Authorization")) {
      String authToken = reqHeaders.getFirst("Authorization");
      if (authToken.equals("afj232hj2332")) { // TODO: Implement actual authorization. Maybe access service class?
        return true;
      }
      else {
        return false;
      }
    }
    else {
      return false;
    }
  }
}
