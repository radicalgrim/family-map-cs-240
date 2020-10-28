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
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        String urlPath = exchange.getRequestURI().toString();
        if (urlPath == null || urlPath.equals("/")) {
          urlPath = "/index.html";
        }
        String filePath = "web" + urlPath;
        File file = new File(filePath);
        if (!file.exists()) {
          filePath = "web/HTML/404.html";
          file = new File(filePath);
        }
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
}


/*
  // 1. How to get the HTTP request type (or, "method")
  // 2. How to access HTTP request headers
  // 3. How to return the desired status code (200, 404, etc.)
  //		in an HTTP response
  // 4. How to write JSON data to the HTTP response body
  // 5. How to check an incoming HTTP request for an auth token
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    try {
      if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
        Headers reqHeaders = exchange.getRequestHeaders();
        if (reqHeaders.containsKey("Authorization")) {
          String authToken=reqHeaders.getFirst("Authorization");
          // Do a database lookup here
          if (authToken.equals("afj232hj2332")) {
            // Do a database lookup here
            String respData =
                    "{ \"game-list\": [" +
                            "{ \"name\": \"fhe game\", \"player-count\": 3 }," +
                            "{ \"name\": \"work game\", \"player-count\": 4 }," +
                            "{ \"name\": \"church game\", \"player-count\": 2 }" +
                            "]" +
                            "}";
            // Start sending the HTTP response to the client, starting with
            // the status code and any defined headers.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            // Now that the status code and headers have been sent to the client,
            // next we send the JSON data in the HTTP response body.

            // Get the response body output stream.
            OutputStream respBody = exchange.getResponseBody();
            // Write the JSON string to the output stream.
            writeString(respData, respBody);
            // Close the output stream.
            respBody.close();
          } else {
            // The auth token was invalid somehow, so we return a "not authorized status code to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
          }
        } else {
          // We did not get an auth token, so we return a "not authorized status code to the client.
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
        }
      } else {
        // We expected a GET but got something else, so we return a "bad request" status code to the client.
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
      }
    } catch (IOException e) {
      // Some kind of internal error has occurred inside the server (not the client's fault), so we return an
      // "internal server error" status code to the client.
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
      // Since the server is unable to complete the request, the client will
      // not receive the list of games, so we close the response body output stream,
      // indicating that the response is complete.
      exchange.getResponseBody().close();
      e.printStackTrace();
    }
  }

  //The writeString method shows how to write a String to an OutputStream.
  private void writeString(String str, OutputStream os) throws IOException {
    OutputStreamWriter sw = new OutputStreamWriter(os);
    BufferedWriter bw = new BufferedWriter(sw);
    bw.write(str);
    bw.flush();
  }
*/

/*

    try {
      if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
        Headers reqHeaders = exchange.getRequestHeaders();
        if (reqHeaders.containsKey("Authorization")) {
          String authToken = reqHeaders.getFirst("Authorization");
          if (authToken.equals("afj232hj2332")) {

            // Extract the JSON string from the HTTP request body

            // Get the request body input stream
            InputStream reqBody = exchange.getRequestBody();

            // Read JSON string from the input stream
            String reqData = readString(reqBody);

            // Display/log the request JSON data
            System.out.println(reqData);


            // Claim a route based on the request data


            // Start sending the HTTP response to the client, starting with
            // the status code and any defined headers.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

          } else {
            // The auth token was invalid somehow, so we return a "not authorized"
            // status code to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
          }
        } else {
          // We did not get an auth token, so we return a "not authorized"
          // status code to the client.
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
        }
      } else {
        // We expected a POST but got something else, so we return a "bad request"
        // status code to the client.
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
      }

      // We are not sending a response body, so close the response body
      // output stream, indicating that the response is complete.
      exchange.getResponseBody().close();
    } catch (IOException e) {
      // Some kind of internal error has occurred inside the server (not the
      // client's fault), so we return an "internal server error" status code
      // to the client.
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);

      // We are not sending a response body, so close the response body
      // output stream, indicating that the response is complete.
      exchange.getResponseBody().close();

      // Display/log the stack trace
      e.printStackTrace();
    }
  }


      The readString method shows how to read a String from an InputStream.

  private String readString(InputStream is) throws IOException {
    StringBuilder sb = new StringBuilder();
    InputStreamReader sr = new InputStreamReader(is);
    char[] buf = new char[1024];
    int len;
    while ((len = sr.read(buf)) > 0) {
      sb.append(buf, 0, len);
    }
    return sb.toString();
  }
}
 */
