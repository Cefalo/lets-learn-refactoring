package com.cefalo.workshop.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by satyajit on 3/26/19.
 */
public class NetworkOperation implements IOOperation {

  private static final String JSON_CONFIG_URL = "https://raw.githubusercontent.com/satyajitdey02/ssh-client/master/src/main/resources/%s";

  private String fileName;

  public NetworkOperation(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public String read() throws IOException {

    StringBuilder sb = new StringBuilder();

    BufferedReader reader;
    URL url = new URL(String.format(JSON_CONFIG_URL, this.fileName));

    reader = new BufferedReader(new InputStreamReader(url.openStream()));
    String inputLine;
    while ((inputLine = reader.readLine()) != null) {
      sb.append(inputLine).append("\n");
    }

    try {
      reader.close();
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
