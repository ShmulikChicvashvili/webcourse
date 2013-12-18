/**
 * 
 */
package com.technion.coolie.server.teletech.framework;

/**
 * @author Argaman
 * 
 */

public class OfficeLocation {
  private String faculty;
  private String officeRoom;

  public OfficeLocation() {
  }

  public OfficeLocation(String faculty, String officeRoom) {
    this.faculty = faculty;
    this.officeRoom = officeRoom;
  }

  /**
   * @return the faculty
   */
  public String faculty() {
    return faculty;
  }

  /**
   * @param faculty
   *          the faculty to set
   */
  public void setFaculty(String faculty) {
    this.faculty = faculty;
  }

  /**
   * @return the officeRoom
   */
  public String officeRoom() {
    return officeRoom;
  }

  /**
   * @param officeRoom
   *          the officeRoom to set
   */
  public void setOfficeRoom(String officeRoom) {
    this.officeRoom = officeRoom;
  }

  @Override
  public String toString() {
    if (faculty == null || officeRoom == null)
      return "TD";
    return faculty + " " + officeRoom;
  }

}
