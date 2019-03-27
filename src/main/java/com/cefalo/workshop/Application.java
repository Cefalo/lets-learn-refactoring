package com.cefalo.workshop;

import com.jcraft.jsch.JSchException;
import java.io.IOException;
import org.apache.commons.lang.StringUtils;

/**
 * Created by satyajit on 3/25/19.
 */
public class Application {

  public static void main(String[] args) {

    String instanceName = (args.length > 0 && StringUtils.isNotBlank(args[0])) ? args[0] : "app1";
    ServerInstanceManager instanceManager = new ServerInstanceManager(instanceName);

    ServerInfo info = instanceManager.getServerInfo();
    if (info == null) {
      System.out.println("No valid Server details found for connection.");
      System.exit(1);
    }

    SshConnectionManager connectionManager = new SshConnectionManager();
    String command = (args.length > 1 && StringUtils.isNotBlank(args[1])) ? args[1] : "ls -la";

    try {
      CLI cli = connectionManager.connectToServer(info.getServer(), info.getCredentials());
      System.out.println(cli.execute(command));
      cli.exit();
    } catch (JSchException | IOException e) {
      e.printStackTrace();
    }
  }

}
