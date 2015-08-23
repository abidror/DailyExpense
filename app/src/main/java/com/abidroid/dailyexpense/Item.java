package com.abidroid.dailyexpense;

public class Item {

	int itemId;
	String itemName;
	
	
	public Item() {
		super();
	}


	public Item(String itemName) {
		super();
		this.itemName = itemName;
	}


	public Item(int itemId, String itemName) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
	}


	public int getItemId() {
		return itemId;
	}


	public void setItemId(int itemId) {
		this.itemId = itemId;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	@Override
	public String toString() {
		return itemName;
	}
	
		
}
