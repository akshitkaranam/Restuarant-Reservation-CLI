package com.company.menuItem;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


import java.util.ArrayList;

public class MenuList {

	private static ArrayList<MenuItem> mItemList = new ArrayList<>();
	Scanner sc = new Scanner(System.in);


	public void createMenuItem() {

		mItemList.add(new MenuItem());
		System.out.println("Enter Name of dish: ");
		String name = sc.nextLine();
		mItemList.get(mItemList.size() - 1).setItemName(name);
		System.out.println("Enter Description of Dish: ");
		String desc = sc.nextLine();
		mItemList.get(mItemList.size() - 1).setItemDescription(desc);
		System.out.println("Enter Price of Dish: ");
		double price = sc.nextDouble();
		String itemType ="";
		mItemList.get(mItemList.size() - 1).setItemPrice(price);

		int typeNum = 1;

		do {
			System.out.println("Enter Type of Dish: \n" + "(1) - Appetizer\n" + "(2) - Main Course\n" + "(3) - Drink\n"
					+ "(4) - Dessert");
			typeNum = sc.nextInt();
			switch (typeNum) {
			case 1:
				mItemList.get(mItemList.size() - 1).setItemType(MenuItem.MenuItemType.APPETIZER);
				itemType = "appetizer";
				processMenuListToCSVFile();
				break;
			case 2:
				mItemList.get(mItemList.size() - 1).setItemType(MenuItem.MenuItemType.MAINCOURSE);
				itemType = "main course";
				processMenuListToCSVFile();
				break;
			case 3:
				mItemList.get(mItemList.size() - 1).setItemType(MenuItem.MenuItemType.DRINKS);
				itemType = "drinks";
				processMenuListToCSVFile();
				break;
			case 4:
				mItemList.get(mItemList.size() - 1).setItemType(MenuItem.MenuItemType.DESSERT);
				itemType = "dessert";
				processMenuListToCSVFile();
				break;
			default:
				System.out.println("Please enter a number between 1 and 4");
				break;
			}


		} while (typeNum < 1 || typeNum > 4);
		sc.nextLine();

	}

	public static void displayMenu() {

		if (mItemList.isEmpty()) {
			System.out.println("No items in Menu!");
		} else {
			// System.out.println("Name \t Description \t Price \t Type");
			for (int i = 0; i < mItemList.size(); i++) {

				System.out.print(mItemList.get(i).getItemName() + "\t");
				System.out.print(mItemList.get(i).getItemDescription() + "\t");
				System.out.print(mItemList.get(i).getItemPrice() + "\t");
				System.out.print(mItemList.get(i).getItemType() + "\n");
			}

		}

		// fix the alignment problem later
		// have to display by categories

	}

	public boolean removeMenuItem() {

		if (mItemList.isEmpty()) {
			System.out.println("No items in Menu!");
			return false;
		} else {
			System.out.println("Enter the name of the item to remove");
			String r = sc.nextLine();
			for (int i = 0; i < mItemList.size(); i++) {
				if (r.equals(mItemList.get(i).getItemName())) {
					mItemList.remove(i);
					processMenuListToCSVFile();
					return true;
				}
			}
			return false;

		}
	}

	public void updateMenuItem() {

		if (mItemList.isEmpty()) {
			System.out.println("No items in Menu!");

		} else {
			System.out.println("Displaying Current Menu");
			displayMenu();
			System.out.println("Select name of item to update");
			String r = sc.nextLine();
			for (int i = 0; i < mItemList.size(); i++) {
				if (r.equals(mItemList.get(i).getItemName())) {
					int update = 1;

					while (update != 5) {
						System.out.println("Select what you would like to update:\n"
								+ "================================\n" + "|1. New Name |\n" + "|2. New Price|\n"
								+ "|3. New Type |\n" + "|4. New Description|\n" + "|5. Quit|\n"
								+ "==================================");
						update = sc.nextInt();

						switch (update) {
						case 1:
							sc.nextLine();
							System.out.println("Enter new Name");
							String name = sc.nextLine();
							mItemList.get(i).setItemName(name);
							break;
						case 2:
							System.out.println("Enter new Price");
							double price = sc.nextDouble();
							mItemList.get(i).setItemPrice(price);
							break;
						case 3:
							int typeNum = 1;
							do {
								System.out.println("Enter Type of Dish: \n" + "(1) - Appetizer\n"
										+ "(2) - Main Course\n" + "(3) - Drink\n" + "(4) - Dessert");
								typeNum = sc.nextInt();
								switch (typeNum) {
								case 1:
									mItemList.get(i).setItemType(MenuItem.MenuItemType.APPETIZER);
									break;
								case 2:
									mItemList.get(i).setItemType(MenuItem.MenuItemType.MAINCOURSE);
									break;
								case 3:
									mItemList.get(i).setItemType(MenuItem.MenuItemType.DRINKS);
									break;
								case 4:
									mItemList.get(i).setItemType(MenuItem.MenuItemType.DESSERT);
									break;
								default:
									System.out.println("Please enter a number between 1 and 4");
									break;
								}

							} while (typeNum < 1 || typeNum > 4);
							break;

						case 4:
							sc.nextLine();
							System.out.println("Enter new Description");
							String desc = sc.nextLine();
							mItemList.get(i).setItemDescription(desc);
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
			}

			processMenuListToCSVFile();

		}

	}

	public static ArrayList<MenuItem> getmItemList() {
		return mItemList;
	}

	public static void processMenuListToCSVFile(){
		try {
			// create a list of objects
			List<List<String>> records = new ArrayList<>();

			for(int i = 0; i<mItemList.size();i++){
				List<String> tempList = new ArrayList<>();
				tempList.add(mItemList.get(i).getItemName());
				tempList.add(mItemList.get(i).getItemDescription());
				tempList.add(Double.toString(mItemList.get(i).getItemPrice()));
				tempList.add(mItemList.get(i).getItemType().name());
				records.add(tempList);
			}

			// create a writer

			FileWriter writer = new FileWriter("src/com/company/menu.csv",false);

			// write all records
			for (List<String> record : records) {
				writer.write(String.join(";", record));
				writer.write("\n");
			}

			//close the writer
			writer.flush();
			writer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
