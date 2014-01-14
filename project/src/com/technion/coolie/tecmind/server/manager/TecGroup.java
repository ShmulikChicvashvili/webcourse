package com.technion.coolie.tecmind.server.manager;

import com.technion.coolie.server.techmine.IGetters;

public class TecGroup implements IGetters {
  String id;
  String name;

  /**
   * @param id1
   * @param name1
   */
  public TecGroup(String id1, String name1) {
    this.id = id1;
    this.name = name1;
  }

  TecGroup() {
  }

  /**
   * @return the id
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * @param id1
   *          the id to set
   */
  public void setId(String id1) {
    this.id = id1;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

}
