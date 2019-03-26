package com.cefalo.workshop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

public class FileOperation implements IOOperation {

  private static final ClassLoader CLASS_LOADER = FileOperation.class.getClassLoader();

  String fileName;

  public FileOperation(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public String read() throws IOException {

    StringBuilder sb = new StringBuilder();

    File file = new File(CLASS_LOADER.getResource(this.fileName).getFile());
    if (file.exists()) {
      FileInputStream fis = null;
      fis = new FileInputStream(file);
      int i = 0;
      while ((i = fis.read()) != -1) {
        sb.append((char) i);
      }

      fis.close();
    }

    return sb.toString();
  }

  @Override
  public boolean write(String content) throws IOException {

    File file = null;
    try {
      file = new File(CLASS_LOADER.getResource(this.fileName).toURI()).getAbsoluteFile();
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
      if (fos != null) {
        fos.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }


}
