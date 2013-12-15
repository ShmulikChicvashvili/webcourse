package com.technion.coolie.server.ug.framework;

import java.io.Serializable;

public class CourseKey implements Serializable {
  private String number;
  private Semester semester;

  CourseKey() {

  }

  public CourseKey(String id, Semester semester) {
    this.number = id;
    this.semester = semester;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  // @Override
  // public boolean equals(Object obj) {
  // if (obj == null)
  // return false;
  // if (obj == this)
  // return true;
  // if (!(obj instanceof Person))
  // return false;
  //
  // CourseKey rhs = (CourseKey) obj;
  // return new EqualsBuilder().
  // // if deriving: appendSuper(super.equals(obj)).
  // append(name, rhs.name).
  // append(age, rhs.age).
  // isEquals();
  // }
  // }
  //

  private static final long serialVersionUID = 939036143890035323L;

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((number == null) ? 0 : number.hashCode());
    result = prime * result + ((semester == null) ? 0 : semester.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    CourseKey other = (CourseKey) obj;
    if (number == null) {
      if (other.number != null)
        return false;
    } else if (!number.equals(other.number))
      return false;
    if (semester == null) {
      if (other.semester != null)
        return false;
    } else if (!semester.equals(other.semester))
      return false;
    return true;
  }

}
