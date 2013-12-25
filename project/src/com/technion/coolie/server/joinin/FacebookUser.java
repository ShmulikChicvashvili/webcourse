package com.technion.coolie.server.joinin;

import java.io.Serializable;

/**
 * 
 * Created on 25/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public class FacebookUser implements Serializable {

  private static final long serialVersionUID = 6384795709625190863L;

  Long id;
  String desc;

  public Long getId() {
    return id;
  }

}
