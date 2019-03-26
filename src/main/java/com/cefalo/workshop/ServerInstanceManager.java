package com.cefalo.workshop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

public class ServerInstanceManager {

  private static final ClassLoader CLASS_LOADER = Application.class.getClassLoader();
  private static final String JSON_CONFIG_URL = "https://github.com/satyajitdey02/ssh-client/blob/master/src/main/resources/app1.json";

  private String instanceName;

  public ServerInstanceManager(String instanceName) {
    this.instanceName = instanceName;
  }

  public String getInstanceName() {
    return instanceName;
  }

  public void setInstanceName(String instanceName) {
    this.instanceName = instanceName;
  }

  public JSONObject getServerInfo() {
    JSONObject config = readLocalConfig();
    if (config == null) {
      config = readRemoteConfig();
      if (config != null) {
        updateLocalConfig(config);
      }
    }

    return config;
  }

  private JSONObject readLocalConfig() {

    JSONObject config = null;
    File configFile = new File(CLASS_LOADER.getResource(this.instanceName + ".json").getFile());

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
}
