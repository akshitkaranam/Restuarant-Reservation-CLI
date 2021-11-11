package com.company.menuItem;

public class MenuItem {
	private String itemName;
	private String itemDescription;
	private double itemPrice;
	private MenuItemType itemType;

	enum MenuItemType {
		APPETIZER, MAINCOURSE, DRINKS, DESSERT;

	}

	public MenuItem(String itemName, String itemDescription, double itemPrice, String menuItemTypeString) {
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.itemPrice = itemPrice;
		menuItemTypeString = menuItemTypeString.replaceAll("\\s+","");

		if(menuItemTypeString.equalsIgnoreCase("appetizer")){
			itemType = MenuItemType.APPETIZER;
		}else if(menuItemTypeString.equalsIgnoreCase("maincourse")){
			itemType = MenuItemType.MAINCOURSE;
		}else if(menuItemTypeString.equalsIgnoreCase("drinks")){
			itemType = MenuItemType.DRINKS;
		}else if(menuItemTypeString.equalsIgnoreCase("dessert")){
			itemType = MenuItemType.DESSERT;
		}



	}

	public MenuItem(){
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemType(MenuItemType itemType) {
		this.itemType = itemType;
	}

	public MenuItemType getItemType() {
		return itemType;
	}
	
	public void displayMenuItems() {
		System.out.println(itemName + " " + itemDescription + " " + itemType);
	}


	@Override
	public String toString() {
		return "MenuItem{" +
				"itemName='" + itemName + '\'' +
				", itemDescription='" + itemDescription + '\'' +
				", itemPrice=" + itemPrice +
				", itemType=" + itemType +
				'}';
	}
}
