package handler;

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