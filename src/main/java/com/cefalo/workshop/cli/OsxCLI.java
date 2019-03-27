package com.cefalo.workshop.cli;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by satyajit on 3/26/19.
 */
public class OsxCLI implements CLI {

  private Session session;

  public OsxCLI(Session session) {
    this.session = session;
  }

  @Override
  public String execute(String command) throws JSchException, IOException {
    System.out.println(String.format("%s@%s:~$ %s",
        session.getUserName(), session.getHost(), command));

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
        sb.append("\n").append("exit-status: ").append(channel.getExitStatus());
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
