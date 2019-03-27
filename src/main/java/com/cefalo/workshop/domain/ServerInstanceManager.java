package com.cefalo.workshop.domain;

import com.cefalo.workshop.cli.CLI;
import com.cefalo.workshop.io.FileOperation;
import com.cefalo.workshop.io.IOOperation;
import com.cefalo.workshop.io.NetworkOperation;
import com.cefalo.workshop.io.decorator.NetworkOperationDecorator;
import com.cefalo.workshop.ssh.SshConnectionManager;
import com.jcraft.jsch.JSchException;
import java.io.IOException;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

/**
 * Created by satyajit on 3/26/19.
 */
public class ServerInstanceManager {

  //TODO: Need to organize this method a bit
  public ServerInfo getServerInfo(String instanceName) {
    ServerInfo serverInfo = null;

    String fileName = instanceName + ".json";
    IOOperation fileOperation = new FileOperation(fileName);
    try {
      String content = fileOperation.read();
      if (StringUtils.isNotBlank(content)) {
        serverInfo = toServerInfo(content);
      } else {
        //Decorate network operation so that it can handle write file operation
        //after network file read
        IOOperation networkOperation = new NetworkOperationDecorator(
            new NetworkOperation(fileName));
        content = networkOperation.read();
        if (StringUtils.isNotBlank(content)) {
          serverInfo = toServerInfo(content);
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    return serverInfo;
  }

  private ServerInfo toServerInfo(String content) {
    JSONObject obj = new JSONObject(content);
    Server server = new Server(obj.getString("host"), obj.getInt("port"));
    Credentials credentials = new Credentials(obj.getString("user"), obj.getString("password"));

    return new ServerInfo(server, credentials);
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
