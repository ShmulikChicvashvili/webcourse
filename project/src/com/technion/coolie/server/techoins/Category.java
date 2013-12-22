package com.technion.coolie.server.techoins;

public enum Category {
  LECTURE_SUMMARIES("Lecture Summaries"), TUTORIAL_SUMMARIES(
      "Tutorial Summaries"), GENERAL_SUMMARIES("General Summaries"), BOOKS(
      "Books"), SUMMARY("Summary"), CLOTHING("Clothing"), ELECTRONICS(
      "Electronics"), OFFICE_SUPPLIES("Office Supplies"), OTHER("Other");
  // if you add categories then don't forget to add values to getValueOf

  private final String value;

  private Category(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    if (value == null)
      return super.toString();
    return value;
  }

  public static Category getValueOf(String str) {
    if (str.equals("Tutorial Summaries")) {
      return Category.TUTORIAL_SUMMARIES;
    } else if (str.equals("Lecture Summaries")) {
      return Category.LECTURE_SUMMARIES;
    } else if (str.equals("General Summaries")) {
      return Category.GENERAL_SUMMARIES;
    } else if (str.equals("Books")) {
      return Category.BOOKS;
    } else if (str.equals("Summary")) {
      return Category.SUMMARY;
    } else if (str.equals("Clothing")) {
      return Category.CLOTHING;
    } else if (str.equals("Electronics")) {
      return Category.ELECTRONICS;
    } else if (str.equals("Office Supplies")) {
      return Category.OFFICE_SUPPLIES;
    } else {
      return Category.OTHER;
    }
  }
}
