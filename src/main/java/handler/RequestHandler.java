package handler;

import com.sun.net.httpserver.HttpExchange;
import service.result.ErrorResult;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public abstract class RequestHandler {

  public void makeResponseBody(HttpExchange exchange, ErrorResult result) throws IOException {
    OutputStream respBody = exchange.getResponseBody();
    String respData = JsonSerializer.serialize(result);
    writeString(respData, respBody);
    respBody.close();
  }

  public void writeString(String str, OutputStream os) throws IOException {
    OutputStreamWriter sw = new OutputStreamWriter(os);
    BufferedWriter bw = new BufferedWriter(sw);
    bw.write(str);
    bw.flush();
  }

}