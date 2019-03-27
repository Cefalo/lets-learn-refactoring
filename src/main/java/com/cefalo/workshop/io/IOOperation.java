package com.cefalo.workshop.io;

import java.io.IOException;

public interface IOOperation {

  public String read() throws IOException;

  public boolean write(String content) throws IOException;

}
