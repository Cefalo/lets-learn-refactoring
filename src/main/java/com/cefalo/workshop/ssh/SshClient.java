package com.cefalo.workshop.ssh;

import com.cefalo.workshop.cli.CLI;
import com.cefalo.workshop.cli.OsxCLI;
import com.cefalo.workshop.domain.Credentials;
import com.cefalo.workshop.domain.Server;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.util.Properties;

/**
 * Created by satyajit on 3/26/19.
 */
public class SshClient {

  private Server server;
  private Credentials credentials;

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

    System.out.println(String.format("Login success full to instance: %s as user: %s",
        server.getHost(), credentials.getUser()));

    return new OsxCLI(session);
  }

}
