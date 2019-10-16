package com.cefalo.workshop.domain;

import java.io.IOException;

/**
 * Created by satyajit on 10/16/19.
 */
public interface CrudRepository {

  public ServerInfo create(ServerInfo info) throws IOException;

  public ServerInfo read() throws IOException;

  public ServerInfo update(ServerInfo info) throws IOException;

  public void delete() throws IOException;

}
