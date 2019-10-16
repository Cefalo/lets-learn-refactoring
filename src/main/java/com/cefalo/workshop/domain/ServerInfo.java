package com.cefalo.workshop.domain;

/**
 * Created by satyajit on 3/26/19.
 */
public class ServerInfo {

  private Server server;
  private Credentials credentials;

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

  @Override
  public String toString() {
    return "{\"host\": \"" + this.server.getHost()
        + "\", \"port\": " + this.server.getPort()
        + ",\"user\": \"" + this.credentials.getUser()
        + "\",\"password\": \"" + this.credentials.getPassword() + "\" }";
  }
}
