package com.cefalo.workshop.cli;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.IOException;

/**
 * Created by satyajit on 3/27/19.
 */
public class SolarisCLI implements CLI {

  private Session session;

  public SolarisCLI(Session session) {
    this.session = session;
  }

  @Override
  public String execute(String command) throws JSchException, IOException {
    //TODO: Implement execute logic specific for Solaris OS
    return null;
  }

  @Override
  public void exit() {
    //TODO: Implement execute logic specific for Solaris OS
  }
}
