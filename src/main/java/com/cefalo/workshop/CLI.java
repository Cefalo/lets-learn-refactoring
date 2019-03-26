package com.cefalo.workshop;

import com.jcraft.jsch.JSchException;
import java.io.IOException;

public interface CLI {

  String execute(String command) throws JSchException, IOException;

  void exit();

}
