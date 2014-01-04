package com.technion.coolie.tecmind.server;

/**
 * 
 * Created on 8/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public enum TechmineClasses {
  USER("user"), TOP_BEST_POST("topBestPost"), TOP_BEST_COMMENT("topBestComment"), TEC_POST(
      "tecPost"), TEC_COMMENT("tecComment"), TEC_LIKE("tecLike");

  private final String value;

  private TechmineClasses(String s) {
    value = s;
  }

  public String value() {
    return value;
  }
}
