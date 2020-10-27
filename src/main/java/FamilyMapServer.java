import com.sun.net.httpserver.HttpServer;
import handler.FileHandler;
import handler.RegisterHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class FamilyMapServer {

  public void main(String[] args) {
    int port = Integer.getInteger(args[0]);

    try {

      startServer(port);

    } catch (IOException e) {
      e.printStackTrace();
    }


  }

  private void startServer(int port) throws IOException {
    InetSocketAddress serverAddress = new InetSocketAddress(port);
    HttpServer server = HttpServer.create(serverAddress, 10);
    registerHandlers(server);
    server.start();
    System.out.println("FamilyMapServer listening on port " + port);
  }

  private void registerHandlers(HttpServer server) {
    server.createContext("/", new FileHandler());
    server.createContext("/user/register", new RegisterHandler());
    // Fill with more handlers
  }

}
