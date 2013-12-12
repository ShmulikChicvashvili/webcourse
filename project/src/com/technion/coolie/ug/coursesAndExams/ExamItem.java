package com.technion.coolie.ug.coursesAndExams;

public class ExamItem {

	private final String moed;
	private final String examDate;

	public ExamItem(String moed, String examDate) {
		this.moed = moed;
		this.examDate = examDate;
	}

	public String getMoed() {
		return moed;
	}

	public String getExamDate() {
		return examDate;
	}

}
