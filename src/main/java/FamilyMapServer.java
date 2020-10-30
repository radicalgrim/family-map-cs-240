import com.sun.net.httpserver.HttpServer;
import handler.ClearHandler;
import handler.FileHandler;
import handler.RegisterHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class FamilyMapServer {

  public static void main(String[] args) {
    int port = Integer.parseInt(args[0]);

    try {
      startServer(port);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private static void startServer(int port) throws IOException {
    InetSocketAddress serverAddress = new InetSocketAddress(port);
    HttpServer server = HttpServer.create(serverAddress, 10);
    registerHandlers(server);
    server.start();
    System.out.println("FamilyMapServer listening on port " + port);
  }

  private static void registerHandlers(HttpServer server) {
    server.createContext("/", new FileHandler());
    server.createContext("/clear", new ClearHandler());

    //Handlers to consider:
    //  RequestHandler
    //    Top-Level parent handler
    //    Code to write a Json response
    //    Other code???
    //  AuthorizingRequestHandler
    //    Parent for handlers that require authorization
    //    Read the authorization header to get the auth_token. Return a 401 if missing or invalid
    //  PostRequestHandler
    //    Parent for handlers that handle post requests
    //    Read the request Json and convert to a Java object
    //    Other code???
    //  Others???
  }

}
