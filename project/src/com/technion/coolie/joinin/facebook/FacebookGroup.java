package com.technion.coolie.joinin.facebook;

/**
 * Class that represents a user group in Facebook
 * 
 * @author Ido
 * 
 */
public class FacebookGroup {
  private final Long gid;
  private final String gname;
  
  /**
   * C'tor
   * 
   * @param groupId
   *          - the group ID of the Facebook group.
   * @param groupName
   *          - the group name of the Facebook group.
   */
  public FacebookGroup(final Long groupId, final String groupName) {
    gid = groupId;
    gname = groupName;
  }
  
  /**
   * 
   * @return Long representing the group Id.
   */
  public Long getGroupId() {
    return gid;
  }
  
  /**
   * 
   * @return String representing the group name.
   */
  public String getGroupName() {
    return gname;
  }
}
