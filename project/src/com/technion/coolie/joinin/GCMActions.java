package com.technion.coolie.joinin;

/**
 * @author Shimon Kama
 * 
 */
public interface GCMActions {
  /**
   * Send this action to an intent in order to register to new event message
   * broadcasts
   */
  public static final String ACTION_ADD_MESSAGE = "il.ac.technion.cs.cs234311.teamapp.message";
  /**
   * Send this action to an intent in order to register to new user joined your
   * event broadcasts
   */
  public static final String ACTION_ATTEND = "il.ac.technion.cs.cs234311.teamapp.attend";
  /**
   * Send this action to an intent in order to register to new user left your
   * event broadcasts
   */
  public static final String ACTION_UNATTEND = "il.ac.technion.cs.cs234311.teamapp.unattend";
  /**
   * Send this action to an intent in order to register to update in an event
   * broadcasts
   */
  public static final String ACTION_EDITED = "il.ac.technion.cs.cs234311.teamapp.edited";
  /**
   * Send this action to an intent in order to register to event deletions
   * broadcasts
   */
  public static final String ACTION_DELETED = "il.ac.technion.cs.cs234311.teamapp.deleted";
  /**
   * Send this action to an intent in order to register to new interesting event
   * broadcasts
   */
  public static final String ACTION_INTEREST = "il.ac.technion.cs.cs234311.teamapp.interest";
  /**
   * Send this action to an intent in order to register to an event reminder
   * broadcasts
   */
  public static final String ACTION_REMINDER = "il.ac.technion.cs.cs234311.teamapp.reminder";
  
  /**
   * 
   * @return The intent action describing the corresponding enum
   */
  public String getAction();
}
