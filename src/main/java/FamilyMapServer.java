import com.sun.net.httpserver.HttpServer;
import handler.*;

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
    server.createContext("/load", new LoadHandler());
    server.createContext("/user/login", new LoginHandler());
    server.createContext("/fill", new FillHandler());
    server.createContext("/user/register", new RegisterHandler());
    server.createContext("/event", new EventHandler());
    server.createContext("/person", new PersonHandler());
  }

}
