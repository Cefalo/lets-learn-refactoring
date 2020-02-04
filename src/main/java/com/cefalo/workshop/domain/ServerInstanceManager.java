package com.cefalo.workshop.domain;

import com.cefalo.workshop.cli.CLI;
import com.cefalo.workshop.io.FileOperation;
import com.cefalo.workshop.io.NetworkOperation;
import com.cefalo.workshop.ssh.SshConnectionManager;
import com.jcraft.jsch.JSchException;
import java.io.IOException;

/**
 * Created by satyajit on 3/26/19.
 */
public class ServerInstanceManager {


  public ServerInfo getServerInfo(String instanceName) {

    ServerInfo serverInfo = null;
    CrudRepository repository = new ServerRepository(new FileOperation(instanceName));

    try {
      serverInfo = repository.read();
      if (serverInfo == null) {
        repository = new ServerRepository(new NetworkOperation(instanceName));
        serverInfo = repository.read();

        repository = new ServerRepository(new FileOperation(instanceName));
        repository.update(serverInfo);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    return serverInfo;
  }


  public CLI connectToServer(String instanceName) {
    System.out.println(String.format("Trying to connect to instance: %s", instanceName));

    CLI cli = null;

    ServerInfo info = getServerInfo(instanceName);
    SshConnectionManager connectionManager = new SshConnectionManager();

    try {
      cli = connectionManager.connectSSH(info.getServer(), info.getCredentials());
    } catch (JSchException e) {
      e.printStackTrace();
    }

    return cli;
  }
}
