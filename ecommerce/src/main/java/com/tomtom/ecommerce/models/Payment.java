package com.tomtom.ecommerce.models;

public class Payment {

	private String credicardNumber;
	
	private String expYear;
	
	private String expMonth;
	
	private int cvv;
	
	private String cardHolderName;

	public String getCredicardNumber() {
		return credicardNumber;
	}

	public void setCredicardNumber(String credicardNumber) {
		this.credicardNumber = credicardNumber;
	}

	public String getExpYear() {
		return expYear;
	}

	public void setExpYear(String expYear) {
		this.expYear = expYear;
	}

	public String getExpMonth() {
		return expMonth;
	}

	public void setExpMonth(String expMonth) {
		this.expMonth = expMonth;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	
	   
	
}
