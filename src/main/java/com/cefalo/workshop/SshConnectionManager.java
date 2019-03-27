package com.cefalo.workshop;

import com.jcraft.jsch.JSchException;

public class SshConnectionManager {

  public CLI connectToServer(Server server, Credentials credentials) throws JSchException {
    SshClient client = new SshClient(server, credentials);
    return client.connect();
  }
}
