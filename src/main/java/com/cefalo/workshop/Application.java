package com.cefalo.workshop;

import com.cefalo.workshop.cli.CLI;
import com.cefalo.workshop.domain.ServerInstanceManager;
import com.jcraft.jsch.JSchException;
import java.io.IOException;
import org.apache.commons.lang.StringUtils;

/**
 * Created by satyajit on 3/25/19.
 */
public class Application {

  public static void main(String[] args) {

    String instanceName = (args.length > 0 && StringUtils.isNotBlank(args[0])) ? args[0] : "app2";
    String command = (args.length > 1 && StringUtils.isNotBlank(args[1])) ? args[1] : "ls -la";

    ServerInstanceManager instanceManager = new ServerInstanceManager();
    CLI cli = instanceManager.connectToServer(instanceName);
    try {
      System.out.println(cli.execute(command));
    } catch (JSchException | IOException e) {
      e.printStackTrace();
    } finally {
      cli.exit();
    }
  }

}
