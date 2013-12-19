package com.technion.coolie.joinin.facebook;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * Class that represents a user fetched from Facebook
 * 
 * @author Ido & Shimon
 * 
 */
public class FacebookUser implements Parcelable, Comparable<FacebookUser> {
  private String fullname;
  private String username;
  
  /**
   * C'tor
   */
  public FacebookUser() {
    fullname = "";
    username = "";
  }
  
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
  
  /**
   * Create a new FacebookUser from Parcel
   * 
   * @param in
   *          the Parcel
   */
  public FacebookUser(final Parcel in) {
    fullname = in.readString();
    username = in.readString();
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
  
  public String toJson() {
    return new Gson().toJson(this);
  }
  
  public static FacebookUser fromJson(final String json) {
    return new Gson().fromJson(json, FacebookUser.class);
  }
  
  @Override public int hashCode() {
    return username == null ? 0 : username.hashCode();
  }
  
  @Override public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    return username.equals(((FacebookUser) obj).getUsername());
  }
  
  /**
   * Compares two Facebook Users by their full name (lexicographic order).
   */
  @Override public int compareTo(final FacebookUser another) {
    return fullname.compareTo(another.getFullname());
  }
  
  @Override public int describeContents() {
    return 0;
  }
  
  @Override public void writeToParcel(final Parcel dest, final int flags) {
    dest.writeString(fullname);
    dest.writeString(username);
  }
  
  @SuppressWarnings({ "rawtypes", "unchecked" }) public static final Parcelable.Creator<FacebookUser> CREATOR = new Parcelable.Creator() {
    @Override public FacebookUser createFromParcel(final Parcel in) {
      return new FacebookUser(in);
    }
    
    @Override public FacebookUser[] newArray(final int size) {
      return new FacebookUser[size];
    }
  };
}
