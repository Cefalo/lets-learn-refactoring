package com.cefalo.workshop.io;

import java.io.IOException;

/**
 * Created by satyajit on 3/26/19.
 */
public interface IOOperation {

  public String read() throws IOException;

  public boolean write(String content) throws IOException;

}
