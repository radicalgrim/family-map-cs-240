package handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class PostHandler extends RequestHandler {

  public String readString(InputStream is) throws IOException {
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
