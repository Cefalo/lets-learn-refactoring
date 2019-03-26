package com.cefalo.workshop;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

/**
 * Created by satyajit on 3/25/19.
 */
public class Application {

  public static void main(String[] args) {

    String instanceName = StringUtils.isNotBlank(args[0]) ? "app1" : args[0];
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
    String command = StringUtils.isNotBlank(args[1]) ? "ls -la" : args[1];
    connectionManager.sendCommand(command);
  }

}
