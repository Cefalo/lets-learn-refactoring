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

  private static final ClassLoader CLASS_LOADER = Application.class.getClassLoader();
  private static final String JSON_CONFIG_URL = "https://raw.githubusercontent.com/satyajitdey02/ssh-client/master/src/main/resources/app2.json";

  JSONObject readLocalConfig() {

    JSONObject config = null;
    File configFile = new File(CLASS_LOADER.getResource("app2.json").getFile());

    if (configFile.exists()) {
      StringBuilder sb = new StringBuilder();
      FileInputStream fis = null;
      try {
        fis = new FileInputStream(configFile);
        int i = 0;
        while ((i = fis.read()) != -1) {
          sb.append((char) i);
        }
        fis.close();
      } catch (IOException e) {
        e.printStackTrace();
      }

      if (StringUtils.isNotBlank(sb.toString())) {
        config = new JSONObject(sb.toString());
      }
    }

    return config;
  }

  private void updateLocalConfig(JSONObject config) {

    FileOutputStream fos = null;

    try {
      File file = new File(CLASS_LOADER.getResource("config.json").toURI()).getAbsoluteFile();
      fos = new FileOutputStream(file);

      byte[] contentInBytes = config.toString().getBytes();

      fos.write(contentInBytes);
      fos.flush();
      fos.close();

      System.out.println("Done");


    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    } finally {
      try {
        if (fos != null) {
          fos.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private JSONObject readRemoteConfig() {
    JSONObject config = null;

    StringBuilder sb = new StringBuilder();
    BufferedReader reader = null;
    URL configUrl;
    try {

      configUrl = new URL(
          JSON_CONFIG_URL);

      reader = new BufferedReader(new InputStreamReader(configUrl.openStream()));
      String inputLine;
      while ((inputLine = reader.readLine()) != null) {
        sb.append(inputLine).append("\n");
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (reader != null) {
          reader.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    String content = sb.toString();
    if (StringUtils.isNotBlank(sb.toString())) {
      config = new JSONObject(content);
    }

    return config;
  }

  public static void main(String[] args) {
    Application app = new Application();

    JSONObject config = app.readLocalConfig();

    if (config == null) {
      config = app.readRemoteConfig();
      if (config != null) {
        app.updateLocalConfig(config);
      } else {
        System.out.println("No Config available to connect to the remote HOST.");
        return;
      }
    }

    String host = config.getString("host");
    String user = config.getString("user");
    String password = config.getString("password");
    String command = "ls -ltr";

    try {
      Properties properties = new Properties();
      properties.put("StrictHostKeyChecking", "no");
      JSch jsch = new JSch();
      Session session = jsch.getSession(user, host, 22);
      session.setPassword(password);
      session.setConfig(properties);
      session.connect();
      System.out.println("Connected");

      Channel channel = session.openChannel("exec");
      ((ChannelExec) channel).setCommand(command);
      channel.setInputStream(null);
      ((ChannelExec) channel).setErrStream(System.err);

      InputStream in = channel.getInputStream();
      channel.connect();
      byte[] tmp = new byte[1024];
      while (true) {
        while (in.available() > 0) {
          int i = in.read(tmp, 0, 1024);
          if (i < 0) {
            break;
          }
          System.out.print(new String(tmp, 0, i));
        }
        if (channel.isClosed()) {
          System.out.println("exit-status: " + channel.getExitStatus());
          break;
        }
        try {
          Thread.sleep(1000);
        } catch (Exception ee) {
        }
      }
      channel.disconnect();
      session.disconnect();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
