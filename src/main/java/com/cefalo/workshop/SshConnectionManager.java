package com.cefalo.workshop;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.InputStream;
import java.util.Properties;

public class SshConnectionManager {

  Server server;
  Credentials credentials;

  public SshConnectionManager(Server server, Credentials credentials) {
    this.server = server;
    this.credentials = credentials;
  }

  public Server getServer() {
    return server;
  }

  public void setServer(Server server) {
    this.server = server;
  }

  public Credentials getCredentials() {
    return credentials;
  }

  public void setCredentials(Credentials credentials) {
    this.credentials = credentials;
  }

  public void sendCommand(String command) {

    try {
      Properties properties = new Properties();
      properties.put("StrictHostKeyChecking", "no");
      JSch jsch = new JSch();
      Session session = jsch.getSession(this.credentials.getUser(),
          this.getServer().getHost(), this.getServer().getPort());
      session.setPassword(this.getCredentials().getPassword());
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
