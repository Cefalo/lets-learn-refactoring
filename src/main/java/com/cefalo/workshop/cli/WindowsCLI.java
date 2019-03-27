package com.cefalo.workshop.cli;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.IOException;

/**
 * Created by satyajit on 3/26/19.
 */
public class WindowsCLI implements CLI {

  private Session session;

  public WindowsCLI(Session session) {
    this.session = session;
  }

  @Override
  public String execute(String command) throws JSchException, IOException {
    //TODO: Implement execute logic specific for Windows OS
    return null;
  }

  @Override
  public void exit() {
    //TODO: Implement execute logic specific for Windows OS
  }
}
