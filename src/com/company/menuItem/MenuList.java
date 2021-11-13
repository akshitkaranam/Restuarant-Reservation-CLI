package com.company.menuItem;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * This is the class that contains a static list of all the MenuItems that are added
 */

public class MenuList {

    private static final ArrayList<MenuItem> mItemList = new ArrayList<>();
    Scanner sc = new Scanner(System.in);


    /**
     * Creates a new MenuItem. The user has to enter inputs accordingly to the prompts given.
     */
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
        mItemList.get(mItemList.size() - 1).setItemPrice(price);

        int typeNum = 1;

        do {
            System.out.println("""
                    Enter Type of Dish:\s
                    (1) - Appetizer
                    (2) - Main Course
                    (3) - Drink
                    (4) - Dessert""");

            typeNum = sc.nextInt();

            switch (typeNum) {
                case 1 -> {
                    mItemList.get(mItemList.size() - 1).setItemType(MenuItem.MenuItemType.APPETIZER);
                    processMenuListToCSVFile();
                }
                case 2 -> {
                    mItemList.get(mItemList.size() - 1).setItemType(MenuItem.MenuItemType.MAINCOURSE);
                    processMenuListToCSVFile();
                }
                case 3 -> {
                    mItemList.get(mItemList.size() - 1).setItemType(MenuItem.MenuItemType.DRINKS);
                    processMenuListToCSVFile();
                }
                case 4 -> {
                    mItemList.get(mItemList.size() - 1).setItemType(MenuItem.MenuItemType.DESSERT);
                    processMenuListToCSVFile();
                }
                default -> System.out.println("Please enter a number between 1 and 4");
            }


        } while (typeNum < 1 || typeNum > 4);
        sc.nextLine();

    }


    /**
     * Display the current MenuItems that are added
     */
    public static void displayMenu() {
        if (mItemList.isEmpty()) {
            System.out.println("No items in Menu!");
        } else {
            for (int j = 0; j < mItemList.size(); j++) {
                System.out.println("Item " + (j + 1) + ": " + mItemList.get(j).getItemName()
                        + " " + mItemList.get(j).getItemDescription()
                        + " $" + mItemList.get(j).getItemPrice()
                        + " " + mItemList.get(j).getItemType());
            }
        }
    }

    /**
     * Removes a MenuItem from the mItemList. User is expected to give inputs based on the prompts provided.
     */

    public void removeMenuItem() {

        if (mItemList.isEmpty()) {
            System.out.println("No items in Menu!");
        } else {
            System.out.println("Enter the name of the item to remove");
            String r = sc.nextLine().toLowerCase();
            for (int i = 0; i < mItemList.size(); i++) {
                if (r.equals(mItemList.get(i).getItemName().toLowerCase())) {
                    mItemList.remove(i);
                    processMenuListToCSVFile();
                    return;
                }
            }

        }
    }

    /**
     * Updates a MenuItem from the mItemList. User is expected to give inputs based on the prompts provided.
     * This function enables the following attributes of the MenuItem to be changes:
     * <li> Name
     * <li> Price
     * <li> Type
     * <li> Description
     */

    public void updateMenuItem() {

        if (mItemList.isEmpty()) {
            System.out.println("No items in Menu!");

        } else {
            System.out.println("Displaying Current Menu");
            displayMenu();
            System.out.println("Select name of item to update");
            String r = sc.nextLine().toLowerCase();
            for (MenuItem menuItem : mItemList) {
                if (r.equals(menuItem.getItemName().toLowerCase())) {
                    int update = 1;

                    while (update != 5) {
                        System.out.println("""
                                Select what you would like to update:
                                ================================
                                |1. New Name |
                                |2. New Price|
                                |3. New Type |
                                |4. New Description|
                                |5. Quit|
                                ==================================""");
                        update = sc.nextInt();

                        switch (update) {
                            case 1 -> {
                                sc.nextLine();
                                System.out.println("Enter new Name");
                                String name = sc.nextLine();
                                menuItem.setItemName(name);
                            }
                            case 2 -> {
                                System.out.println("Enter new Price");
                                double price = sc.nextDouble();
                                menuItem.setItemPrice(price);
                            }
                            case 3 -> {
                                int typeNum = 1;
                                do {
                                    System.out.println("""
                                            Enter Type of Dish:\s
                                            (1) - Appetizer
                                            (2) - Main Course
                                            (3) - Drink
                                            (4) - Dessert""");
                                    typeNum = sc.nextInt();
                                    switch (typeNum) {
                                        case 1 -> menuItem.setItemType(MenuItem.MenuItemType.APPETIZER);
                                        case 2 -> menuItem.setItemType(MenuItem.MenuItemType.MAINCOURSE);
                                        case 3 -> menuItem.setItemType(MenuItem.MenuItemType.DRINKS);
                                        case 4 -> menuItem.setItemType(MenuItem.MenuItemType.DESSERT);
                                        default -> System.out.println("Please enter a number between 1 and 4");
                                    }

                                } while (typeNum < 1 || typeNum > 4);
                            }
                            case 4 -> {
                                sc.nextLine();
                                System.out.println("Enter new Description");
                                String desc = sc.nextLine();
                                menuItem.setItemDescription(desc);
                            }
                            case 5 -> System.out.println("Exiting update Menu");
                            default -> System.out.println("Enter number between 1-5");
                        }

                    }

                }
            }

            processMenuListToCSVFile();

        }

    }

    public static MenuItem getMenuItemFromList(String name) {
        for (MenuItem mItem : mItemList) {
            if (mItem.getItemName().equals(name)) {
                return mItem;
            }
        }

        return null;
    }

    /**
     * This method returns the List of MenuItemObjects that have been added.
     *
     * @return mItemList (List of MenuItem Objects that have been added)
     */

    public static ArrayList<MenuItem> getmItemList() {
        return mItemList;
    }

    /**
     * This method processes the mItemList and writes to a CSV file with the necessary information, so that the
     * added MenuItems can be retrieved by reading the generated the CSV File when the application is relaunched.
     */

    public static void processMenuListToCSVFile() {
        try {
            // create a list of objects
            List<List<String>> records = new ArrayList<>();

            for (MenuItem menuItem : mItemList) {
                List<String> tempList = new ArrayList<>();
                tempList.add(menuItem.getItemName());
                tempList.add(menuItem.getItemDescription());
                tempList.add(Double.toString(menuItem.getItemPrice()));
                tempList.add(menuItem.getItemType().name());
                records.add(tempList);
            }

            // create a writer

            FileWriter writer = new FileWriter("src/com/company/storeddata/menu.csv", false);

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
