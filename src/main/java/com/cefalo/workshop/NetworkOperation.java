package com.cefalo.workshop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class NetworkOperation implements IOOperation {

  private static final String JSON_CONFIG_URL = "https://raw.githubusercontent.com/satyajitdey02/ssh-client/master/src/main/resources/%s";

  String fileName;

  public NetworkOperation(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public String read() throws IOException {

    StringBuilder sb = new StringBuilder();

    BufferedReader reader = null;
    URL url = new URL(String.format(JSON_CONFIG_URL, this.fileName));

    reader = new BufferedReader(new InputStreamReader(url.openStream()));
    String inputLine;
    while ((inputLine = reader.readLine()) != null) {
      sb.append(inputLine).append("\n");
    }

    try {
      if (reader != null) {
        reader.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return sb.toString();
  }

  @Override
  public boolean write(String content) throws IOException {
    throw new IOException("Remote server instance files are read only.");
  }
}
