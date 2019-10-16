package com.cefalo.workshop;

import com.cefalo.workshop.cli.CLI;
import com.cefalo.workshop.domain.ServerInstanceManager;
import com.jcraft.jsch.JSchException;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by satyajit on 3/28/19.
 */
public class SshClientTests {

  private static final String SERVER_INSTANCE = "app1";
  private static final String COMMAND = "whoami";
  private CLI cli;

  @Before
  public void before() {
    ServerInstanceManager instanceManager = new ServerInstanceManager();
    cli = instanceManager.connectToServer(SERVER_INSTANCE);
  }

  @Test
  public void testSshConnection() throws IOException, JSchException {
    cli.execute(COMMAND);
  }

  @After
  public void after() {
    cli.exit();
  }

}
