package com.cefalo.workshop.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * Created by satyajit on 3/26/19.
 */
public class FileOperation implements IOOperation {

  private static final String CONFIG_FILE_EXT = ".json";
  private static final ClassLoader CLASS_LOADER = FileOperation.class.getClassLoader();

  private String fileName;

  public FileOperation(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public String read() throws IOException {

    StringBuilder sb = new StringBuilder();

    File file = new File(Objects.requireNonNull(CLASS_LOADER.getResource(this.fileName + CONFIG_FILE_EXT)).getFile());
    if (file.exists()) {
      FileInputStream fis = new FileInputStream(file);
      int i;
      while ((i = fis.read()) != -1) {
        final char c = (char) i;
        sb.append(c);
      }

      fis.close();
    }

    return sb.toString();
  }

  @Override
  public boolean write(String content) throws IOException {

    File file;
    try {
      file = new File(Objects.requireNonNull(
          CLASS_LOADER.getResource(this.fileName)).toURI()).getAbsoluteFile();
    } catch (URISyntaxException e) {
      e.printStackTrace();
      return false;
    }

    FileOutputStream fos = new FileOutputStream(file);
    byte[] contentInBytes = content.getBytes();

    fos.write(contentInBytes);
    fos.flush();
    fos.close();

    try {
      fos.close();
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }


}
