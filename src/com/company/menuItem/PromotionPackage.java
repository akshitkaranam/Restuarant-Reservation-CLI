package com.company.menuItem;

import java.util.*;

/**
 * This is the basic entity that is used in PromotionPackageMenu
 */


public class PromotionPackage {

    private final List<MenuItem> promotionPackage;
    private double packagePrice;
    private String packageDescription;
    private String packageName;
    Scanner sc = new Scanner(System.in);

    public PromotionPackage(List<MenuItem> promotionPackage, double packagePrice
            , String packageDescription, String packageName) {
        this.promotionPackage = promotionPackage;
        this.packagePrice = packagePrice;
        this.packageDescription = packageDescription;
        this.packageName = packageName;
    }

    public PromotionPackage() {
        promotionPackage = new ArrayList<>();

    }

    /**
     * returns the package price
     *
     * @return package price
     */
    public double getPackagePrice() {
        return packagePrice;
    }

    /**
     * Displays the package details
     */
    public void displayPackage() {
        System.out.println(packageName + "\nPrice:\t$" + packagePrice);
        for (int i = 0; i < promotionPackage.size(); i++) {
            System.out.println("Item " + (i + 1) + ": " + promotionPackage.get(i).getItemName()
                    + " " + promotionPackage.get(i).getItemDescription()
                    + " " + promotionPackage.get(i).getItemType());
        }
    }

    /**
     * Creates a new PromotionPackage. The user has to enter inputs accordingly to the prompts given.
     */
    public void createPackage() {
        List<MenuItem> menuItemList = MenuList.getmItemList();
        if (menuItemList.isEmpty()) {
            System.out.println("There are currently no items in the menu, please add items in Menu first!");
            return;
        }

        System.out.println("Starting to create a new package...");

        int option;
        System.out.println("Please enter the desired name for the package: ");
        this.packageName = sc.nextLine();
        System.out.println("Please enter package description: ");
        this.packageDescription = sc.nextLine();
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
                    for (j = 0; j < menuItemsInMenu.size(); j++) {
                        System.out.println(j + 1 + " " + MenuList.getmItemList().get(j));
                    }

                    while (true) {
                        innerChosenOption = sc.nextInt();
                        innerChosenOption--;
                        if (innerChosenOption <= j - 1 && innerChosenOption >= 0) {
                            break;
                        } else {
                            System.out.println("Please enter a valid choice!");
                        }
                    }
                    MenuItem relevantItemToBeAdded = menuItemsInMenu.get(innerChosenOption);
                    if (!this.promotionPackage.contains(relevantItemToBeAdded)) {
                        this.promotionPackage.add(relevantItemToBeAdded);
                        System.out.println("Successfully added: " + relevantItemToBeAdded.getItemName());
                    } else {
                        System.out.println(relevantItemToBeAdded.getItemName() + " already exists in this package!");
                    }
                    break;

                case 2:
                    List<MenuItem> addedMenuItemsInPackage = this.promotionPackage;
                    for (j = 0; j < addedMenuItemsInPackage.size(); j++) {
                        System.out.println(j + 1 + " " + addedMenuItemsInPackage.get(j));
                    }

                    while (true) {
                        innerChosenOption = sc.nextInt();
                        innerChosenOption--;
                        if (innerChosenOption <= j - 1 && innerChosenOption >= 0) {
                            break;
                        } else {
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
                    for (j = 0; j < addedMenuItemsInPackage.size(); j++) {
                        System.out.println(j + 1 + " " + addedMenuItemsInPackage.get(j));
                    }
                    break;

                case 4:
                    System.out.println("Please enter the Price you want to set: ");
                    this.packagePrice = sc.nextDouble();


                    System.out.println("Going back to Package Menu");
                    break;
                default:
                    System.out.println("Please enter options from (1-4)");

            }

        } while (option != 4);


    }

    /**
     * Updates a MenuItem from the mItemList. User is expected to give inputs based on the prompts provided.
     * This function enables the following attributes of the MenuItem to be changes:
     * - Name
     * - Add MenuItem
     * - Remove MenuItem
     * - Change Price
     */

    public void updatePackage() {
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
                    System.out.println("Current package name: " + this.packageName + ".\nEnter the new package name: ");
                    this.packageName = sc.nextLine();
                    break;

                case 2:
                    List<MenuItem> menuItemsInMenu = MenuList.getmItemList();
                    int innerChosenOption;
                    int j;
                    for (j = 0; j < menuItemsInMenu.size(); j++) {
                        System.out.println(j + 1 + " " + MenuList.getmItemList().get(j));
                    }

                    while (true) {
                        innerChosenOption = sc.nextInt();
                        innerChosenOption--;
                        if (innerChosenOption <= j - 1 && innerChosenOption >= 0) {
                            break;
                        } else {
                            System.out.println("Please enter a valid choice!");
                        }
                    }
                    MenuItem relevantItemToBeAdded = menuItemsInMenu.get(innerChosenOption);
                    if (!this.promotionPackage.contains(relevantItemToBeAdded)) {
                        this.promotionPackage.add(relevantItemToBeAdded);
                        System.out.println("Successfully added: " + relevantItemToBeAdded.getItemName());
                    } else {
                        System.out.println(relevantItemToBeAdded.getItemName() + " already exists in this package!");
                    }
                    break;

                case 3:
                    List<MenuItem> addedMenuItemsInPackage = this.promotionPackage;
                    if (this.promotionPackage.isEmpty()) {
                        System.out.println("Sorry there are no MenuItems Added in to this package!");
                    }

                    for (j = 0; j < addedMenuItemsInPackage.size(); j++) {
                        System.out.println(j + 1 + " " + addedMenuItemsInPackage.get(j));
                    }

                    while (true) {
                        innerChosenOption = sc.nextInt();
                        innerChosenOption--;
                        if (innerChosenOption <= j - 1 && innerChosenOption >= 0) {
                            System.out.println("Successfully removed: "
                                    + addedMenuItemsInPackage.get(innerChosenOption).getItemName());
                            addedMenuItemsInPackage.remove(innerChosenOption);
                            break;
                        } else {
                            System.out.println("Please enter a valid choice!");
                        }
                    }

                    break;

                case 4:
                    addedMenuItemsInPackage = this.promotionPackage;
                    for (j = 0; j < addedMenuItemsInPackage.size(); j++) {
                        System.out.println("Item " + (j + 1) + ": " + promotionPackage.get(j).getItemName()
                                + " " + promotionPackage.get(j).getItemDescription()
                                + " " + promotionPackage.get(j).getItemType());
                    }

                    break;

                case 5:
                    System.out.println("Please enter the Price you want to set: ");
                    this.packagePrice = sc.nextDouble();
                    break;
                case 6:
                    System.out.println("Going back to Package Menu");
                    break;
                default:
                    System.out.println("Please enter options from (1-4)");

            }

        } while (option != 6);
    }


    /**
     * Returns the name of this package.
     *
     * @return the name of this package
     */

    public String getPackageName() {
        return packageName;
    }

    @Override
    public String toString() {
        return "{" +
                "packageName='" + packageName + '\'' +
                ", packageDescription='" + packageDescription + '\'' +
                ", packagePrice=" + packagePrice +
                '}';
    }

    /**
     * Returns the list of MenuItems in this PromotionPackage object
     *
     * @return the list of MenuItems in this PromotionPackage object
     */
    public List<MenuItem> getPromotionPackage() {
        return promotionPackage;
    }

    public String getPackageDescription() {
        return packageDescription;
    }


}
