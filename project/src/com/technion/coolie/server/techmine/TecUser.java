package com.technion.coolie.server.techmine;

import java.util.Date;

public class TecUser implements IGetters {
  String id;
  String name;
  TecUserTitle title;
  Date lastMining;
  int totalTechoins;
  int bankAccount;
  int commentsNum;
  int postsNum;
  int likesNum;
  int likeOnPostsNum;

  /**
   * @param id1
   * @param name1
   * @param title1
   * @param lastMining1
   * @param totalTechoins1
   * @param bankAccount1
   */
  public TecUser(String id1, String name1, TecUserTitle title1,
      Date lastMining1, int totalTechoins1, int bankAccount1, int commentsNum1,
      int postsNum1, int likesNum1, int likeOnPostsNum1) {
    this.id = id1;
    this.name = name1;
    this.title = title1;
    this.lastMining = lastMining1;
    this.totalTechoins = totalTechoins1;
    this.bankAccount = bankAccount1;
    this.commentsNum = commentsNum1;
    this.postsNum = postsNum1;
    this.likesNum = likesNum1;
    this.likeOnPostsNum = likeOnPostsNum1;
  }

  TecUser() {
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
   * @param name1
   *          the name to set
   */
  public void setName(String name1) {
    this.name = name1;
  }

  /**
   * @return the title
   */
  public TecUserTitle getTitle() {
    return title;
  }

  /**
   * @param title1
   *          the title to set
   */
  public void setTitle(TecUserTitle title1) {
    this.title = title1;
  }

  /**
   * @return the lastMining
   */
  public Date getLastMining() {
    return lastMining;
  }

  /**
   * @param lastMining1
   *          the lastMining to set
   */
  public void setLastMining(Date lastMining1) {
    this.lastMining = lastMining1;
  }

  /**
   * @return the totalTechoins
   */
  public int getTotalTechoins() {
    return totalTechoins;
  }

  /**
   * @return the commentsNum
   */
  public int getCommentsNum() {
    return commentsNum;
  }

  /**
   * @return the postsNum
   */
  public int getPostsNum() {
    return postsNum;
  }

  /**
   * @param totalTechoins1
   *          the totalTechoins to set
   */
  public void setTotalTechoins(int totalTechoins1) {
    this.totalTechoins = totalTechoins1;
  }

  /**
   * @param commentsNum1
   *          the commentsNum to set
   */
  public void setCommentsNum(int commentsNum1) {
    this.commentsNum = commentsNum1;
  }

  /**
   * @param postsNum1
   *          the postsNum to set
   */
  public void setPostsNum(int postsNum1) {
    this.postsNum = postsNum1;
  }

  /**
   * @return the bankAccount
   */
  public int getBankAccount() {
    return bankAccount;
  }

  /**
   * @param bankAccount1
   *          the bankAccount to set
   */
  public void setBankAccount(int bankAccount1) {
    this.bankAccount = bankAccount1;
  }

}
