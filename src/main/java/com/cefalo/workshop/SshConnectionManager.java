package com.cefalo.workshop;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.InputStream;
import java.util.Properties;

public class SshConnectionManager {

  public CLI connectToServer(Server server, Credentials credentials) throws JSchException {
    SshClient client = new SshClient(server, credentials);
    return client.connect();
  }
}
