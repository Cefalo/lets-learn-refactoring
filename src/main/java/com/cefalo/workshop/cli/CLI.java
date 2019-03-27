package com.cefalo.workshop.cli;

import com.jcraft.jsch.JSchException;
import java.io.IOException;

/**
 * Created by satyajit on 3/26/19.
 */

public interface CLI {

  String execute(String command) throws JSchException, IOException;

  void exit();

}
