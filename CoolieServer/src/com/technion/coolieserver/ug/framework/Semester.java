package com.technion.coolieserver.ug.framework;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Embed;

@Embed
public class Semester implements Serializable, Comparable<Semester> {

  int year;
  SemesterSeason ss;

  public Semester() {

  }

  public Semester(int year, SemesterSeason ss) {
    this.year = year;
    this.ss = ss;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public SemesterSeason getSs() {
    return ss;
  }

  public void setSs(SemesterSeason ss) {
    this.ss = ss;
  }

  private static final long serialVersionUID = 7728978870620022481L;

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ss == null) ? 0 : ss.hashCode());
    result = prime * result + year;
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
    Semester other = (Semester) obj;
    if (ss != other.ss)
      return false;
    if (year != other.year)
      return false;
    return true;
  }

  @Override
  public int compareTo(Semester another) {
    if (another == null || this.ss == null || another.ss == null)
      return 0;
    if (this.year != another.year)
      return this.year - another.year;
    else
      return this.ss.ordinal() - another.ss.ordinal();
  }
}
