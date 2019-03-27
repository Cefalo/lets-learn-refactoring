package com.cefalo.workshop;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

/**
 * Created by satyajit on 3/25/19.
 */
public class Application {

  public static void main(String[] args) {

    String instanceName = (args.length == 0 || StringUtils.isBlank(args[0])) ? "app2" : args[0];
    ServerInstanceManager instanceManager = new ServerInstanceManager(instanceName);

    JSONObject config = instanceManager.getServerInfo();
    if (config == null) {
      System.out.println("No valid Server details found for connection.");
      System.exit(1);
    }

    String host = config.getString("host");
    int port = config.getInt("port");
    String user = config.getString("user");
    String password = config.getString("password");

    SshConnectionManager connectionManager = new SshConnectionManager(host, port, user, password);
    String command = (args.length <= 1 || StringUtils.isBlank(args[1])) ? "ls -la" : args[1];
    connectionManager.sendCommand(command);
  }

}
