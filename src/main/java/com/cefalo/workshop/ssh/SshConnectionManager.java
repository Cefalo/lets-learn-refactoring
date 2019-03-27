package com.cefalo.workshop.ssh;

import com.cefalo.workshop.cli.CLI;
import com.cefalo.workshop.domain.Credentials;
import com.cefalo.workshop.domain.Server;
import com.jcraft.jsch.JSchException;

public class SshConnectionManager {

  public CLI connectSSH(Server server, Credentials credentials) throws JSchException {
    SshClient client = new SshClient(server, credentials);
    return client.connect();
  }
}
