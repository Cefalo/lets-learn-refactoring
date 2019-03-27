package com.cefalo.workshop.cli;

import com.cefalo.workshop.utils.SystemUtils;
import com.jcraft.jsch.Session;

/**
 * Created by satyajit on 3/27/19.
 */
public class CliFactory {

  public static CLI getCLI(Session session) {

    if (SystemUtils.isUnix()) {
      return new UnixCLI(session);
    }

    if (SystemUtils.isMac()) {
      return new OsxCLI(session);
    }

    if (SystemUtils.isSolaris()) {
      return new SolarisCLI(session);
    }

    return new WindowsCLI(session);
  }

}
