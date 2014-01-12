package com.technion.coolie.server.techoins;

public enum Category {
  LECTURE_SUMMARIES("Lecture Summaries"), TOTURIAL_SUMMARIES(
      "Toturial Summaries"), SUMMARY("Summary"), CLOTHING("Clothing"), ELECTRONICS(
      "Electronics"), OFFICE_SUPPLIES("Office Supplies"), OTHER("Other");

  private final String name;

  private Category(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    if (name == null)
      return super.toString();
    return name;
  }
}
