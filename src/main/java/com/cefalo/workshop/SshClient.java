package com.cefalo.workshop;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.util.Properties;

public class SshClient {

  Server server;
  Credentials credentials;

  public SshClient(Server server, Credentials credentials) {
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

  public CLI connect() throws JSchException {
    Properties properties = new Properties();
    properties.put("StrictHostKeyChecking", "no");
    JSch jsch = new JSch();
    Session session = jsch.getSession(this.credentials.getUser(),
        this.getServer().getHost(), this.getServer().getPort());
    session.setPassword(this.getCredentials().getPassword());
    session.setConfig(properties);
    session.connect();

    return new MacCLI(session);
  }



}
