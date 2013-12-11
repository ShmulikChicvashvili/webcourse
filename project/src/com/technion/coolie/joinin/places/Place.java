package com.technion.coolie.joinin.places;

/**
 * Place datatype as returned from Google Places server
 * 
 * @author Shimon Kama
 * 
 */
public class Place {
  private String id;
  private String name;
  private String reference;
  private String icon;
  private String vicinity;
  private Geometry geometry;
  private String formatted_address;
  private String formatted_phone_number;
  
  public static class Geometry {
    public Location location;
  }
  
  public static class Location {
    public double lat;
    public double lng;
  }
  
  /**
   * For Gson only
   */
  @Deprecated public Place() {
  }
  
  /**
   * @return the id
   */
  public String getId() {
    return id;
  }
  
  /**
   * @param id
   *          the id to set
   */
  public void setId(final String id) {
    this.id = id;
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
  public void setName(final String name) {
    this.name = name;
  }
  
  /**
   * @return the reference
   */
  public String getReference() {
    return reference;
  }
  
  /**
   * @param reference
   *          the reference to set
   */
  public void setReference(final String reference) {
    this.reference = reference;
  }
  
  /**
   * @return the icon
   */
  public String getIcon() {
    return icon;
  }
  
  /**
   * @param icon
   *          the icon to set
   */
  public void setIcon(final String icon) {
    this.icon = icon;
  }
  
  /**
   * @return the vicinity
   */
  public String getVicinity() {
    return vicinity;
  }
  
  /**
   * @param vicinity
   *          the vicinity to set
   */
  public void setVicinity(final String vicinity) {
    this.vicinity = vicinity;
  }
  
  /**
   * @return the geometry
   */
  public Geometry getGeometry() {
    return geometry;
  }
  
  /**
   * @param geometry
   *          the geometry to set
   */
  public void setGeometry(final Geometry geometry) {
    this.geometry = geometry;
  }
  
  /**
   * @return the formatted_address
   */
  public String getFormatted_address() {
    return formatted_address;
  }
  
  /**
   * @param formatted_address
   *          the formatted_address to set
   */
  public void setFormatted_address(final String formatted_address) {
    this.formatted_address = formatted_address;
  }
  
  /**
   * @return the formatted_phone_number
   */
  public String getFormatted_phone_number() {
    return formatted_phone_number;
  }
  
  /**
   * @param formatted_phone_number
   *          the formatted_phone_number to set
   */
  public void setFormatted_phone_number(final String formatted_phone_number) {
    this.formatted_phone_number = formatted_phone_number;
  }
}