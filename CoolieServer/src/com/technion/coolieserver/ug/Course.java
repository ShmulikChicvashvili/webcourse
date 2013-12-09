package com.technion.coolieserver.ug;

import java.util.Date;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Serialize;

@Entity
public class Course {
  @Id
  private Long id;
  private Semester semester;
  @Index
  private String courseNumber;
  @Index
  private String name;
  @Index
  private String points;
  @Index
  private String description;
  @Index
  private Faculty faculty;// can be a string in the db.
  @Index
  private Date moedA;
  @Index
  private Date moedB;
  private List<String> prerequisites;// kda-mim
  @Serialize
  private List<RegistrationGroup> registrationGroups;

  /**
   * 
   * @param semester_
   * @param courseNumber_
   * @param name_
   * @param points_
   * @param description_
   * @param faculty_
   * @param moedA_
   * @param moedB_
   * @param prerequisites_
   * @param registrationGroups_
   */
  public Course(Semester semester_, String courseNumber_, String name_,
      String points_, String description_, Faculty faculty_, Date moedA_,
      Date moedB_, List<String> prerequisites_,
      List<RegistrationGroup> registrationGroups_) {
    semester = semester_;
    courseNumber = courseNumber_;
    name = name_;
    points = points_;
    description = description_;
    faculty = faculty_;
    moedA = moedA_;
    moedB = moedB_;
    prerequisites = prerequisites_;
    registrationGroups = registrationGroups_;
  }

  Course() {
  }

  /**
   * @return the semester
   */
  public Semester semester() {
    return semester;
  }

  /**
   * @param semester_
   *          the semester to set
   */
  public void setSemester(Semester semester_) {
    semester = semester_;
  }

  /**
   * @return the courseNumber
   */
  public String courseNumber() {
    return courseNumber;
  }

  /**
   * @param courseNumber_
   *          the courseNumber to set
   */
  public void setCourseNumber(String courseNumber_) {
    courseNumber = courseNumber_;
  }

  /**
   * @return the name
   */
  public String name() {
    return name;
  }

  /**
   * @param name_
   *          the name to set
   */
  public void setName(String name_) {
    name = name_;
  }

  /**
   * @return the points
   */
  public String points() {
    return points;
  }

  /**
   * @param points_
   *          the points to set
   */
  public void setPoints(String points_) {
    points = points_;
  }

  /**
   * @return the description
   */
  public String description() {
    return description;
  }

  /**
   * @param description_
   *          the description to set
   */
  public void setDescription(String description_) {
    description = description_;
  }

  /**
   * @return the faculty
   */
  public Faculty faculty() {
    return faculty;
  }

  /**
   * @param faculty_
   *          the faculty to set
   */
  public void setFaculty(Faculty faculty_) {
    faculty = faculty_;
  }

  /**
   * @return the moedA
   */
  public Date moedA() {
    return moedA;
  }

  /**
   * @param moedA_
   *          the moedA to set
   */
  public void setMoedA(Date moedA_) {
    moedA = moedA_;
  }

  /**
   * @return the moedB
   */
  public Date moedB() {
    return moedB;
  }

  /**
   * @param moedB_
   *          the moedB to set
   */
  public void setMoedB(Date moedB_) {
    moedB = moedB_;
  }

  /**
   * @return the prerequisites
   */
  public List<String> prerequisites() {
    return prerequisites;
  }

  /**
   * @param prerequisites_
   *          the prerequisites to set
   */
  public void setPrerequisites(List<String> prerequisites_) {
    prerequisites = prerequisites_;
  }

  /**
   * @return the registrationGroups
   */
  public List<RegistrationGroup> registrationGroups() {
    return registrationGroups;
  }

  /**
   * @param registrationGroups_
   *          the registrationGroups to set
   */
  public void setRegistrationGroups(List<RegistrationGroup> registrationGroups_) {
    registrationGroups = registrationGroups_;
  }

}
