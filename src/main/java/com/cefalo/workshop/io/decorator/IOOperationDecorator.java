package com.cefalo.workshop.io.decorator;

import com.cefalo.workshop.io.IOOperation;
import java.io.IOException;

public class IOOperationDecorator implements IOOperation {

  IOOperation ioOperation;

  public IOOperationDecorator(IOOperation ioOperation) {
    this.ioOperation = ioOperation;
  }

  @Override
  public String read() throws IOException {
    return this.ioOperation.read();
  }

  @Override
  public boolean write(String content) throws IOException {
    return this.ioOperation.write(content);
  }
}
