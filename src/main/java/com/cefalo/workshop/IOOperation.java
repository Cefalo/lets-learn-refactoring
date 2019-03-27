package com.cefalo.workshop;

import java.io.IOException;

public interface IOOperation {

  public String read() throws IOException;

  public boolean write(String content) throws IOException;

}
