package com.technion.coolie.joinin.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.technion.coolie.joinin.facebook.FacebookUser;

public class ClientAccount extends Account implements Parcelable {
  public ClientAccount(final String username, final String fId, final String name) {
    super(username, fId, name);
  }
  
  /**
   * Part of the Parcel interface.
   * 
   * @param in
   */
  public ClientAccount(final Parcel in) {
    super(in.readString(), in.readString(), in.readString());
    setInterests(Long.valueOf(in.readString()).longValue());
  }
  
  /**
   * Use for Gson only.
   */
  @Deprecated public ClientAccount() {
  }
  
  public ClientAccount(final Account a) {
    super(a.getUsername(), a.getFacebookId(), a.getName());
    setInterests(a.getInterests());
  }
  
  public static ClientAccount toClientAccount(final String s) {
    final Account $ = Account.toAccount(s);
    return $ == null ? null : new ClientAccount($);
  }
  
  @Override public int describeContents() {
    return 0;
  }
  
  @Override public void writeToParcel(final Parcel dest, final int flags) {
    dest.writeString(getUsername());
    dest.writeString(getFacebookId());
    dest.writeString(getName());
    dest.writeString(String.valueOf(getInterests()));
  }
  
  @SuppressWarnings("rawtypes") public static final Parcelable.Creator<?> CREATOR = new Parcelable.Creator() {
    @Override public Account createFromParcel(final Parcel in) {
      return new ClientAccount(in);
    }
    
    @Override public Account[] newArray(final int size) {
      return new Account[size];
    }
  };
  
  public FacebookUser toFacebookUser() {
    return new FacebookUser(getName(), getUsername());
  }
  
  /**
   * 
   * @return A JSON representing string of this ClientAccount
   */
  public String toJson() {
    return new Gson().toJson(this);
  }
  
  /**
   * 
   * @param json
   *          A JSON representing string of ClientAccount
   * @return the corresponding ClientAccount
   */
  public static ClientAccount fromJson(final String json) {
    return new Gson().fromJson(json, ClientAccount.class);
  }
}
