package com.cefalo.workshop;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.IOException;
import java.io.InputStream;

public class MacCLI implements CLI {

  Session session;

  public MacCLI(Session session) {
    this.session = session;
  }

  public Session getSession() {
    return session;
  }

  public void setSession(Session session) {
    this.session = session;
  }

  @Override
  public String execute(String command) throws JSchException, IOException {
    StringBuilder sb = new StringBuilder();

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
        sb.append(new String(tmp, 0, i));
      }

      if (channel.isClosed()) {
        sb.append("\n").append("exit-status: " + channel.getExitStatus());
        break;
      }

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    channel.disconnect();

    return sb.toString();

  }

  @Override
  public void exit() {
    session.disconnect();
  }
}
