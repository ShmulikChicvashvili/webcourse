package com.technion.coolie.server.joinin;

public class FacebookUser implements Comparable<FacebookUser> {

  Long id;
  String fullname = "";
  String username = "";

  /**
   * C'tor
   * 
   * @param fullname
   *          - the full name of the Facebook user.
   * @param username
   *          - the Facebook username of the user.
   */
  public FacebookUser(final String fullname, final String username) {
    this.fullname = fullname;
    this.username = username;
  }

  public Long getId() {
    return id;
  }

  FacebookUser() {
  }

  /**
   * 
   * @return String of full name
   */
  public String getFullname() {
    return fullname;
  }

  /**
   * Sets the full name of this Facebook user.
   * 
   * @param fullname
   *          - the full name of this Facebook user.
   */
  public void setFullname(final String fullname) {
    this.fullname = fullname;
  }

  /**
   * 
   * @return String of user name
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username of this Facebook user.
   * 
   * @param username
   *          - the username of this Facebook user.
   */
  public void setUsername(final String username) {
    this.username = username;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return username == null ? 0 : username.hashCode();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    return username.equals(((FacebookUser) obj).getUsername());
  }

  @Override
  public int compareTo(final FacebookUser another) {
    return fullname.compareTo(another.getFullname());
  }

}
