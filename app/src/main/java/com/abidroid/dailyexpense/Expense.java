package com.abidroid.dailyexpense;

public class Expense {

	int expenseId;
	String expenseItemName;
	int itemPrice;
	long expenseDate;
	
	
	public Expense() {
		super();
	}


	public Expense(String expenseItemName, int itemPrice, long expenseDate) {
		super();
		this.expenseItemName = expenseItemName;
		this.itemPrice = itemPrice;
		this.expenseDate = expenseDate;
	}


	public Expense(int expenseId, String itemName, int itemPrice,
			long expenseDate) {
		super();
		this.expenseId = expenseId;
		this.expenseItemName = itemName;
		this.itemPrice = itemPrice;
		this.expenseDate = expenseDate;
	}


	public int getExpenseId() {
		return expenseId;
	}


	public void setExpenseId(int expenseId) {
		this.expenseId = expenseId;
	}


	public String getExpenseItemName() {
		return expenseItemName;
	}


	public void setExpenseItemName(String itemName) {
		this.expenseItemName = itemName;
	}


	public int getItemPrice() {
		return itemPrice;
	}


	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}


	public long getExpenseDate() {
		return expenseDate;
	}


	public void setExpenseDate(long expenseDate) {
		this.expenseDate = expenseDate;
	}
	
	
	
}
