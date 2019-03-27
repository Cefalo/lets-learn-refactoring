package com.cefalo.workshop.io.decorator;

import com.cefalo.workshop.io.FileOperation;
import com.cefalo.workshop.io.IOOperation;
import com.cefalo.workshop.io.NetworkOperation;
import java.io.IOException;
import org.apache.commons.lang.StringUtils;

public class NetworkOperationDecorator extends IOOperationDecorator {

  public NetworkOperationDecorator(IOOperation ioOperation) {
    super(ioOperation);
  }


  @Override
  public String read() throws IOException {
    String content = super.read();
    if (StringUtils.isNotBlank(content)) {
      NetworkOperation networkOperation = (NetworkOperation) super.ioOperation;
      new FileOperation(networkOperation.getFileName()).write(content);
    }

    return content;
  }

  @Override
  public boolean write(String content) throws IOException {
    return super.write(content);
  }
}
