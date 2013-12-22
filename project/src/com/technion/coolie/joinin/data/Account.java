package com.technion.coolie.joinin.data;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technion.coolie.joinin.map.EventType;

/**
 * This class represents an account.
 * 
 * @author Shimon Kama
 * 
 */
public class Account {
  private String username;
  private String fId;
  private String name;
  private long interests;
  
  /**
   * Use only for Gson
   */
  @Deprecated public Account() {
  }
  
  public Account(final String username, final String fId, final String name) {
    this.username = username;
    this.fId = fId;
    this.name = name;
    interests = 0;
  }
  
  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }
  
  /**
   * @param username
   *          the username to set
   */
  public void setUsername(final String username) {
    this.username = username;
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
   * returns the set of interests as a long. Use static 'isIntrested' method in
   * order to get information from this number.
   * 
   * @return a long describing the set of interests.
   */
  public long getInterests() {
    return interests;
  }
  
  /**
   * clears the set of interests.
   */
  public void clearInterests() {
    interests = 0;
  }
  
  public void setInterests(final long interests) {
    this.interests = interests;
  }
  
  /**
   * Adds a category to the list of interests.
   * 
   * @param et
   *          an event type category.
   */
  public void addInterest(final EventType et) {
    interests = interests | 1 << et.ordinal();
  }
  
  /**
   * Removes a category to the list of interests.
   * 
   * @param et
   *          an event type category.
   */
  public void removeInterest(final EventType et) {
    interests = interests & ~(1 << et.ordinal());
  }
  
  /**
   * 
   * @param et
   *          event type category.
   * @return true iff the account is interested in the given category.
   */
  public boolean isInterested(final EventType et) {
    return Account.isInterested(interests, et);
  }
  
  /**
   * 
   * @return a set of the account's interests.
   */
  public Set<EventType> getIntrestsSet() {
    final Set<EventType> $ = new HashSet<EventType>();
    for (final EventType et : EventType.values())
      if (isInterested(et))
        $.add(et);
    return $;
  }
  
  /**
   * returns a serialization of the account using the Gson library.
   */
  @Override public String toString() {
    return new Gson().toJson(this);
  }
  
  /**
   * returns an account that matches the serialization given.
   * 
   * @param s
   *          a serialization of an account.
   * @return the account.
   */
  public static Account toAccount(final String s) {
    return new Gson().fromJson(s, Account.class);
  }
  
  /**
   * Two accounts are equal iff they have the same username.
   */
  @Override public boolean equals(final Object o) {
    if (o == null || o.getClass() != getClass())
      return false;
    if (o == this)
      return true;
    return ((Account) o).username.equals(username);
  }
  
  @Override public int hashCode() {
    return username.hashCode();
  }
  
  /**
   * 
   * @param as
   *          a list of accounts.
   * @return a serialization of the list usig Gson library.
   */
  public static String stringOf(final List<Account> as) {
    return new Gson().toJson(as, new TypeToken<List<Account>>() {
      // EMPTY BLOCK
    }.getType());
  }
  
  /**
   * 
   * @param s
   *          a serialization of account list.
   * @return a list of accounts corresponding to the serialization given.
   */
  public static List<Account> toAccountList(final String s) {
    return new Gson().fromJson(s, new TypeToken<List<Account>>() {
      // EMPTY BLOCK
    }.getType());
  }
  
  public static boolean isInterested(final long intrests, final EventType et) {
    return (intrests & 1 << et.ordinal()) != 0;
  }
  
  public String getFacebookId() {
    return fId;
  }
  
  public void setFacebookId(final String fId) {
    this.fId = fId;
  }
}
