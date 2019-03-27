package com.cefalo.workshop;

import java.io.IOException;
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

  public JSONObject getServerInfo() {
    JSONObject info = null;

    String fileName = this.instanceName + ".json";
    IOOperation fileOperation = new FileOperation(fileName);
    try {
      String content = fileOperation.read();
      if (StringUtils.isNotBlank(content)) {
        return new JSONObject(content);
      }

      IOOperation networkOperation = new NetworkOperation(fileName);
      content = networkOperation.read();
      if (StringUtils.isNotBlank(content)) {
        fileOperation.write(content);
      }

      return new JSONObject(content);

    } catch (IOException e) {
      e.printStackTrace();
    }

    return info;
  }
}
