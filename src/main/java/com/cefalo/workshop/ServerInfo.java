package com.cefalo.workshop;

public class ServerInfo {

  Server server;
  Credentials credentials;

  public ServerInfo(Server server, Credentials credentials) {
    this.server = server;
    this.credentials = credentials;
  }

  public Server getServer() {
    return server;
  }

  public void setServer(Server server) {
    this.server = server;
  }

  public Credentials getCredentials() {
    return credentials;
  }

  public void setCredentials(Credentials credentials) {
    this.credentials = credentials;
  }
}
