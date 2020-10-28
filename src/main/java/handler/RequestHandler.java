package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public abstract class RequestHandler {


  public void writeString(String str, OutputStream os) throws IOException {
    OutputStreamWriter sw = new OutputStreamWriter(os);
    BufferedWriter bw = new BufferedWriter(sw);
    bw.write(str);
    bw.flush();
  }

}

// Try:
// Check that its the right method (GET or POST)
// Check for authorization
// GET:
  // Do a database lookup and get response data
// POST:
  // Put the request body in an input stream
  // Read in the request data
  // Log the request data
// Send a response header, and any other headers
// GET:
  // Put the response body in an output stream
  // Write JSON string to output stream
// Close output stream