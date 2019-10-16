package com.cefalo.workshop.domain;

import com.cefalo.workshop.io.IOOperation;
import java.io.IOException;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

/**
 * Created by satyajit on 10/16/19.
 */
public class ServerRepository implements CrudRepository{

  private IOOperation ioOperation;

  public ServerRepository(IOOperation ioOperation) {
    this.ioOperation = ioOperation;
  }

  //Implement whenever needed
  @Override
  public ServerInfo create(ServerInfo info) throws IOException {
    return null;
  }

  @Override
  public ServerInfo read() throws IOException {
    String content = ioOperation.read();
    if(StringUtils.isBlank(content)) {
      return null;
    }

    return toServerInfo(content);
  }

  @Override
  public ServerInfo update(ServerInfo info) throws IOException {
    ioOperation.write(info.toString());
    return info;
  }

  //Implement whenever needed
  @Override
  public void delete() throws IOException {
    //Delete server from the file associated with current IO Operation
  }

  private ServerInfo toServerInfo(String content) {
    JSONObject obj = new JSONObject(content);
    Server server = new Server(obj.getString("host"), obj.getInt("port"));
    Credentials credentials = new Credentials(obj.getString("user"), obj.getString("password"));

    return new ServerInfo(server, credentials);
  }

  public IOOperation getIoOperation() {
    return ioOperation;
  }

  public void setIoOperation(IOOperation ioOperation) {
    this.ioOperation = ioOperation;
  }

}
