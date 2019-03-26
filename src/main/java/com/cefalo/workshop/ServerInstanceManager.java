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

  public ServerInfo getServerInfo() {
    ServerInfo serverInfo = null;

    String fileName = this.instanceName + ".json";
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
}
