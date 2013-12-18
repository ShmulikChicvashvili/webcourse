package com.technion.coolie.joinin.data;


import com.google.gson.Gson;
import com.technion.coolie.joinin.GCMTag;

public class GCMMessage {
  Account mAccount;
  Event mEvent;
  String mMessage;
  GCMTag mTag;
  
  /**
   * for Gson only!
   */
  @Deprecated public GCMMessage() {
  }
  
  public GCMMessage(final Account account, final Event event, final String message, final GCMTag tag) {
    mAccount = account;
    mEvent = event;
    mMessage = message;
    mTag = tag;
  }
  
  /**
   * @return the account
   */
  public Account getAccount() {
    return mAccount;
  }
  
  /**
   * @param account
   *          the amAccount to set
   */
  public void setAccount(final Account account) {
    mAccount = account;
  }
  
  /**
   * @return the event
   */
  public Event getEvent() {
    return mEvent;
  }
  
  /**
   * @param event
   *          the event to set
   */
  public void setEvent(final Event event) {
    mEvent = event;
  }
  
  /**
   * @return the message
   */
  public String getMessage() {
    return mMessage;
  }
  
  /**
   * @param message
   *          the mMessage to set
   */
  public void setMessage(final String message) {
    mMessage = message;
  }
  
  /**
   * @return the tag
   */
  public GCMTag getTag() {
    return mTag;
  }
  
  /**
   * @param tag
   *          the tag to set
   */
  public void setTag(final GCMTag tag) {
    mTag = tag;
  }
  
  /**
   * 
   * @return a JSON representation of this object
   */
  public String toJson() {
    return new Gson().toJson(this);
  }
  
  /**
   * 
   * @param json
   *          JSON formatted string of a GCMMessage
   * @return the corresponding GCMMessage
   */
  public static GCMMessage fromJson(final String json) {
    return new Gson().fromJson(json, GCMMessage.class);
  }
}
