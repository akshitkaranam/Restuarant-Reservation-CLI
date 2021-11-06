package com.company.menuItem;

import java.util.*;


public class PromotionPackage {

	private ArrayList<MenuItem> promotionPackage;
	private double packagePrice;
	private String packageDescription;
	private String packageName;
	Scanner sc = new Scanner(System.in);

	public PromotionPackage() {

		promotionPackage = new ArrayList<MenuItem>();

	}

	public String getPackageDescription() {
		return packageDescription;
	}

	public void setPackageDescription(String packageDescription) {
		this.packageDescription = packageDescription;
	}

	public double getPackagePrice() {
		return packagePrice;
	}

	public void setPackagePrice(double packagePrice) {
		this.packagePrice = packagePrice;

	}

	public void createPackage() {

		System.out.println("Enter Name of Package");
		String name = sc.nextLine();
		packageName = name;

		System.out.println("Enter price of package");
		double price = sc.nextDouble();
		packagePrice = price;

		System.out.println("How many items in package?");
		int numInPackage = sc.nextInt();
		sc.nextLine();
		for (int i = 0; i < numInPackage; i++) {
			promotionPackage.add(new MenuItem());
			System.out.println("Creating Package Item " + (i + 1));
			System.out.println("Enter Item Name");
			String itemName = sc.nextLine();
			promotionPackage.get(i).setItemName(itemName);
			System.out.println("Enter Item Description");
			String itemDesc = sc.nextLine();
			promotionPackage.get(i).setItemDescription(itemDesc);

			int typeNum = 1;

			do {
				System.out.println("Enter Type of Dish: \n" + "(1) - Appetizer\n" + "(2) - Main Course\n"
						+ "(3) - Drink\n" + "(4) - Dessert");
				typeNum = sc.nextInt();
				switch (typeNum) {
				case 1:
					promotionPackage.get(i).setItemType(MenuItem.MenuItemType.APPETIZER);
					break;
				case 2:
					promotionPackage.get(i).setItemType(MenuItem.MenuItemType.MAINCOURSE);
					break;
				case 3:
					promotionPackage.get(i).setItemType(MenuItem.MenuItemType.DRINKS);
					break;
				case 4:
					promotionPackage.get(i).setItemType(MenuItem.MenuItemType.DESSERT);
					break;
				default:
					System.out.println("Please enter a number between 1 and 4");
					break;
				}

			} while (typeNum < 1 || typeNum > 4);
			sc.nextLine();


		}

	}

	public void removePackageItem() {
		
		System.out.println("Enter Item Number to remove");
		int idx = sc.nextInt();
		promotionPackage.remove(idx-1);

	}
	
	public void addPackageItem() {
		promotionPackage.add(new MenuItem());
		System.out.println("Enter Item Name");
		String itemName = sc.nextLine();
		promotionPackage.get(promotionPackage.size()-1).setItemName(itemName);
		System.out.println("Enter Item Description");
		String itemDesc = sc.nextLine();
		promotionPackage.get(promotionPackage.size()-1).setItemDescription(itemDesc);

		int typeNum = 1;

		do {
			System.out.println("Enter Type of Dish: \n" + "(1) - Appetizer\n" + "(2) - Main Course\n"
					+ "(3) - Drink\n" + "(4) - Dessert");
			typeNum = sc.nextInt();
			switch (typeNum) {
			case 1:
				promotionPackage.get(promotionPackage.size()-1).setItemType(MenuItem.MenuItemType.APPETIZER);
				break;
			case 2:
				promotionPackage.get(promotionPackage.size()-1).setItemType(MenuItem.MenuItemType.MAINCOURSE);
				break;
			case 3:
				promotionPackage.get(promotionPackage.size()-1).setItemType(MenuItem.MenuItemType.DRINKS);
				break;
			case 4:
				promotionPackage.get(promotionPackage.size()-1).setItemType(MenuItem.MenuItemType.DESSERT);
				break;
			default:
				System.out.println("Please enter a number between 1 and 4");
				break;
			}

		} while (typeNum < 1 || typeNum > 4);
		sc.nextLine();
		
		
		
	}

	public void updatePackageItem() {
		
		int update = 1;

		while (update != 4) {
			System.out.println("Select what you would like to update:\n"
					+ "================================\n" + "|1. Change Package Name |\n" + "|2. Change Package Price|\n"
					+"|3. Change Package Description|\n"
					+ "|4. Change Package Item Details |\n" + "|5. Quit|\n"
					+ "==================================");
			update = sc.nextInt();

			switch (update) {
			case 1:
				sc.nextLine();
				System.out.println("Enter new Package Name");
				String name = sc.nextLine();
				setPackageName(name);
				break;
			case 2:
				System.out.println("Enter new Package Price");
				double price = sc.nextDouble();
				setPackagePrice(price);
				break;
				
			case 3:
				sc.nextLine();
				System.out.println("Enter new Package Description");
				String desc = sc.nextLine();
				setPackageDescription(desc);
				break;
			case 4:
				System.out.println("Printing Promotion Package");
				for(int i = 0; i<promotionPackage.size(); i++) {
					System.out.print(i+1 +": ");
					promotionPackage.get(i).displayMenuItems();
				}
				
				System.out.println("Select item number to change");
				int changeidx = sc.nextInt();
				
				int choice = 1;
				do {
					System.out.println("Select what you would like to update:\n"
							+ "================================\n" + "|1. New Name |\n" + "|2. New Description|\n"
							+ "|3. New Type |\n" + "|4. Quit|\n"
							+ "==================================");
					choice = sc.nextInt();
					
					switch(choice) {
					
					case 1:
						System.out.println("Enter new Item Name");
						String itemName = sc.nextLine();
						promotionPackage.get(changeidx).setItemName(itemName);
						break;
					case 2:
						System.out.println("Enter new Item Description");
						String itemDesc = sc.nextLine();
						promotionPackage.get(changeidx).setItemName(itemDesc);
						break;
					case 3:
						int typeNum = 1;

						do {
							System.out.println("Enter Type of Dish: \n" + "(1) - Appetizer\n" + "(2) - Main Course\n"
									+ "(3) - Drink\n" + "(4) - Dessert");
							typeNum = sc.nextInt();
							switch (typeNum) {
							case 1:
								promotionPackage.get(changeidx).setItemType(MenuItem.MenuItemType.APPETIZER);
								break;
							case 2:
								promotionPackage.get(changeidx).setItemType(MenuItem.MenuItemType.MAINCOURSE);
								break;
							case 3:
								promotionPackage.get(changeidx).setItemType(MenuItem.MenuItemType.DRINKS);
								break;
							case 4:
								promotionPackage.get(changeidx).setItemType(MenuItem.MenuItemType.DESSERT);
								break;
							default:
								System.out.println("Please enter a number between 1 and 4");
								break;
							}

						} while (typeNum < 1 || typeNum > 4);
						sc.nextLine();
		
					}
					
				}while(choice !=5);

				break;


			case 5:
				System.out.println("Exiting update Menu");
				break;
			default:
				System.out.println("Enter number between 1-5");
				break;
			}

		}
		

	}

	public void displayPackage() {
		System.out.println(packageName + "\t" + packagePrice);
		for(int i =0; i<promotionPackage.size(); i++) {
			System.out.println("Item Number " + (i+1) + " " + promotionPackage.get(i).getItemName()
					+" "+ promotionPackage.get(i).getItemDescription()
					+" "+ promotionPackage.get(i).getItemType());
			
		}

	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

}
