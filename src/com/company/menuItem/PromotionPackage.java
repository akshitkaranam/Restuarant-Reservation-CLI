package com.company.menuItem;

import com.company.restaurantfunctions.Restaurant;

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


	public void removePackageItem() {
		
		System.out.println("Enter Item Number to remove");
		int idx = sc.nextInt();
		promotionPackage.remove(idx-1);

	}


	public void displayPackage() {
		System.out.println(packageName + "\t" + packagePrice);
		for(int i =0; i<promotionPackage.size(); i++) {
			System.out.println("Item Number " + (i+1) + " " + promotionPackage.get(i).getItemName()
					+" "+ promotionPackage.get(i).getItemDescription()
					+" "+ promotionPackage.get(i).getItemType());
			
		}

	}

	public void updatePackage(){
		int option = 1;
		do {


			System.out.println("""
                    Choose an option:
                    ================================
                    |1. Change Package Name|
                    |2. Add MenuItem to Package
                    |3. Remove MenuItem from Package|
                    |4. Show Added MenuItems to Package|
                    |5. Change price of package|
                    |6. Quit Making Changes to Package|
                    ==================================""");

			option = sc.nextInt();
			switch (option) {

				case 1:
					sc.nextLine();
					System.out.println("Current package name: " +  this.packageName + ". Enter the new package name: ");
					this.packageName = sc.nextLine();
					break;

				case 2:
					List<MenuItem> menuItemsInMenu = MenuList.getmItemList();
					int innerChosenOption;
					int j;
					for(j =0;j<menuItemsInMenu.size();j++){
						System.out.println(j+ " " + MenuList.getmItemList().get(j));
					}

					while(true){
						innerChosenOption = sc.nextInt();
						if(innerChosenOption <= j-1 && innerChosenOption >= 0){
							break;
						}else{
							System.out.println("Please enter a valid choice!");
						}
					}
					MenuItem relevantItemToBeAdded = menuItemsInMenu.get(innerChosenOption);
					if(!this.promotionPackage.contains(relevantItemToBeAdded)){
						this.promotionPackage.add(relevantItemToBeAdded);
						System.out.println("Successfully added: " + relevantItemToBeAdded.getItemName());
					}else{
						System.out.println(relevantItemToBeAdded.getItemName() + " already exists in this package!");
					}
					break;

				case 3:
					List<MenuItem> addedMenuItemsInPackage = this.promotionPackage;
					innerChosenOption = 0;
					j= 0;
					for(j =0;j<addedMenuItemsInPackage.size();j++){
						System.out.println(j+ " " + addedMenuItemsInPackage.get(j));
					}

					while(true){
						innerChosenOption = sc.nextInt();
						if(innerChosenOption <= j-1 && innerChosenOption >= 0){
							System.out.println("Successfully removed: "
									+ addedMenuItemsInPackage.get(innerChosenOption).getItemName());
							addedMenuItemsInPackage.remove(innerChosenOption);
							break;
						}else{
							System.out.println("Please enter a valid choice!");
						}
					}

					break;

				case 4:
					addedMenuItemsInPackage = this.promotionPackage;
					innerChosenOption = 0;
					j= 0;
					for(j =0;j<addedMenuItemsInPackage.size();j++){
						System.out.println(j+ " " + addedMenuItemsInPackage.get(j));
					}
					break;

				case 5:
					System.out.println("Please enter the Price you want to set: ");
					this.packagePrice = sc.nextInt();
					break;
				case 6:
					System.out.println("Going back to Package Menu");
					break;
				default:
					System.out.println("Please enter options from (1-4)");

			}

		} while (option !=6);
	}





	public void createPackage(){
		List<MenuItem> menuItemList = MenuList.getmItemList();
		if(menuItemList.isEmpty()){
			System.out.println("There are currently no items in the menu, please add items in Menu first!");
			return;
		}

		System.out.println("Starting to create a new package");

		int option;
		System.out.println("Please enter the desired name for the package");
		this.packageName = sc.nextLine();
		do {


			System.out.println("""
                    Choose an option:
                    ================================
                    |1. Add MenuItem to Package|
                    |2. Remove MenuItem from Package|
                    |3. Show Added MenuItems to Package|
                    |4. Quit Making Changes to Package|
                    ==================================""");

			option = sc.nextInt();
			switch (option) {

				case 1:
					List<MenuItem> menuItemsInMenu = MenuList.getmItemList();
					int innerChosenOption;
					int j;
					for(j =0;j<menuItemsInMenu.size();j++){
						System.out.println(j+ " " + MenuList.getmItemList().get(j));
					}

					while(true){
						innerChosenOption = sc.nextInt();
						if(innerChosenOption <= j-1 && innerChosenOption >= 0){
							break;
						}else{
							System.out.println("Please enter a valid choice!");
						}
					}
					MenuItem relevantItemToBeAdded = menuItemsInMenu.get(innerChosenOption);
					if(!this.promotionPackage.contains(relevantItemToBeAdded)){
						this.promotionPackage.add(relevantItemToBeAdded);
						System.out.println("Successfully added: " + relevantItemToBeAdded.getItemName());
					}else{
						System.out.println(relevantItemToBeAdded.getItemName() + " already exists in this package!");
					}
					break;

				case 2:
					List<MenuItem> addedMenuItemsInPackage = this.promotionPackage;
					for(j =0;j<addedMenuItemsInPackage.size();j++){
						System.out.println(j+ " " + addedMenuItemsInPackage.get(j));
					}

					while(true){
						innerChosenOption = sc.nextInt();
						if(innerChosenOption <= j-1 && innerChosenOption >= 0){
							break;
						}else{
							System.out.println("Please enter a valid choice!");
						}
					}
					System.out.println("Successfully removed: "
							+ addedMenuItemsInPackage.get(innerChosenOption).getItemName());
					addedMenuItemsInPackage.remove(innerChosenOption);
					break;

				case 3:
					System.out.println(this);
					addedMenuItemsInPackage = this.promotionPackage;
					for(j =0;j<addedMenuItemsInPackage.size();j++){
						System.out.println(j + " " + addedMenuItemsInPackage.get(j));
					}
					break;

				case 4:
					System.out.println("Please enter the Price you want to set: ");
					this.packagePrice = sc.nextInt();


					System.out.println("Going back to Package Menu");
					break;
				default:
					System.out.println("Please enter options from (1-4)");

			}

		} while (option !=4);



	}
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	@Override
	public String toString() {
		return "{" +
				"packageName='" + packageName + '\'' +
				", packageDescription='" + packageDescription + '\'' +
				", packagePrice=" + packagePrice +
				'}';
	}

	public ArrayList<MenuItem> getPromotionPackage() {
		return promotionPackage;
	}
}
