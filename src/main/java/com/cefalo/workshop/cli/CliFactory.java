package com.cefalo.workshop.cli;

import com.jcraft.jsch.Session;

/**
 * Created by satyajit on 3/27/19.
 */
public class CliFactory {

  private static String OS = System.getProperty("os.name").toLowerCase();

  public static CLI getCLI(Session session) {

    if (isUnix()) {
      return new UnixCLI(session);
    }

    if (isMac()) {
      return new OsxCLI(session);
    }

    if (isSolaris()) {
      return new SolarisCLI(session);
    }

    return new WindowsCLI(session);
  }

  private static boolean isWindows() {
    return (OS.contains("win"));
  }

  private static boolean isMac() {
    return (OS.contains("mac"));
  }

  private static boolean isUnix() {
    return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
  }

  private static boolean isSolaris() {
    return (OS.contains("sunos"));
  }

}
