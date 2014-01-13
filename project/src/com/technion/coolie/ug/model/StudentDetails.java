package com.technion.coolie.ug.model;

public class StudentDetails {
	private String average;
	private String successPercentage;
	private String accumulatedPoints;

	public StudentDetails(String average, String successPercentage,
			String accumulatedPoints) {
		this.average = average;
		this.successPercentage = successPercentage;
		this.accumulatedPoints = accumulatedPoints;
	}

	public String getAverage() {
		return average;
	}

	public String getSuccessPercentage() {
		return successPercentage;
	}

	public String getAccumulatedPoints() {
		return accumulatedPoints;
	}
}
