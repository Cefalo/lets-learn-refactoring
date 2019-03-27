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
    JSONObject configObject = null;
    ClassLoader classLoader = Application.class.getClassLoader();

    final URL fileURL = classLoader.getResource("app2.json");
    if (fileURL != null) {
      File configFile = new File(fileURL.getFile());

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
          configObject = new JSONObject(sb.toString());
        } else {
          BufferedReader reader = null;
          URL configUrl;
          try {
            configUrl = new URL(
                "https://raw.githubusercontent.com/satyajitdey02/ssh-client/master/src/main/resources/app2.json");

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
        }

        String content = sb.toString();
        configObject = new JSONObject(content);
        FileOutputStream fop = null;
        File file;

        try {
          file = new File(fileURL.toURI()).getAbsoluteFile();
          fop = new FileOutputStream(file);

          byte[] contentInBytes = content.getBytes();

          fop.write(contentInBytes);
          fop.flush();
          fop.close();

          System.out.println("Done");


        } catch (IOException | URISyntaxException e) {
          e.printStackTrace();
        } finally {
          try {
            if (fop != null) {
              fop.close();
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }

      } else {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        URL configUrl;
        try {
          configUrl = new URL(
              "https://raw.githubusercontent.com/satyajitdey02/ssh-client/master/src/main/resources/app2.json");

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
        configObject = new JSONObject(content);

        FileOutputStream fop = null;
        File file;

        try {
          file = new File(fileURL.toURI()).getAbsoluteFile();
          fop = new FileOutputStream(file);

          byte[] contentInBytes = content.getBytes();

          fop.write(contentInBytes);
          fop.flush();
          fop.close();

          System.out.println("Done");


        } catch (IOException | URISyntaxException e) {
          e.printStackTrace();
        } finally {
          try {
            if (fop != null) {
              fop.close();
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }

    String host = configObject.getString("host");
    String user = configObject.getString("user");
    String password = configObject.getString("password");
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
      System.out.println("DONE");
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
