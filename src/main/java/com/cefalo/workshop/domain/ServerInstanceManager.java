package com.cefalo.workshop.domain;

import com.cefalo.workshop.cli.CLI;
import com.cefalo.workshop.io.FileOperation;
import com.cefalo.workshop.io.IOOperation;
import com.cefalo.workshop.io.NetworkOperation;
import com.cefalo.workshop.ssh.SshConnectionManager;
import com.jcraft.jsch.JSchException;
import java.io.IOException;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

public class ServerInstanceManager {

  public ServerInfo getServerInfo(String instanceName) {
    ServerInfo serverInfo = null;

    String fileName = instanceName + ".json";
    IOOperation fileOperation = new FileOperation(fileName);
    try {
      String content = fileOperation.read();
      if (StringUtils.isNotBlank(content)) {
        serverInfo = toServerInfo(content);
      }

      IOOperation networkOperation = new NetworkOperation(fileName);
      content = networkOperation.read();
      if (StringUtils.isNotBlank(content)) {
        fileOperation.write(content);
      }

      serverInfo = toServerInfo(content);

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
