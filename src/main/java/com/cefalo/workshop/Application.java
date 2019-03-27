package com.cefalo.workshop;

import org.apache.commons.lang.StringUtils;

/**
 * Created by satyajit on 3/25/19.
 */
public class Application {

  public static void main(String[] args) {

    String instanceName = (args.length > 0 && StringUtils.isNotBlank(args[0])) ? args[0] : "app2";
    ServerInstanceManager instanceManager = new ServerInstanceManager(instanceName);

    ServerInfo serverInfo = instanceManager.getServerInfo();
    if (serverInfo == null) {
      System.out.println("No valid Server details found for connection.");
      System.exit(1);
    }

    SshConnectionManager connectionManager = new SshConnectionManager(serverInfo.getServer(),
        serverInfo.getCredentials());
    String command = (args.length > 1 && StringUtils.isNotBlank(args[1])) ? args[1] : "ls -la";
    connectionManager.sendCommand(command);
  }

}
