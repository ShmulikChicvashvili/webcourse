package com.technion.coolie.ug.model;

public class Payment {
	private String description;

	public Payment(String description, String amount) {
		super();
		this.description = description;
		this.amount = amount;
	}

	private String amount;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

}