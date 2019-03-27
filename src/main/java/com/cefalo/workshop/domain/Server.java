package com.cefalo.workshop.domain;

/**
 * Created by satyajit on 3/26/19.
 */
public class Server {

  private String host;
  private int port;

  public Server(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }
}
