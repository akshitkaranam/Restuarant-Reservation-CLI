package com.company.menuItem;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * This is the class that contains a static list of all the PromotionPackage Objects that are added
 */

public class PromotionPackageMenu {

    private static final ArrayList<PromotionPackage> packageList = new ArrayList<>();
    Scanner sc = new Scanner(System.in);


    public PromotionPackageMenu() {
    }

    /**
     * add a new Promotion Package to the static list
     */
    public void addPromotionPackage() {

        packageList.add(new PromotionPackage());
        packageList.get(packageList.size() - 1).createPackage();
        processMenuListToCSVFile();
    }

    /**
     * display the promotion package
     */
    public void displayPackageMenu() {
        if (packageList.size() == 0) {
            System.out.println("There are no promotions yet!");
            return;
        }
        for (int i = 0; i < packageList.size(); i++) {
            System.out.println("Package Number " + (i + 1));
            packageList.get(i).displayPackage();
            System.out.println(" ");
        }
    }

    /**
     * remove a given promotion package based on user inputs
     */
    public void removePromotionPackage() {
        int i;
        if (packageList.size() == 0) {
            System.out.println("There are no promotions to remove!");
            return;
        }
        for (i = 0; i < packageList.size(); i++) {
            System.out.println(i + " " + packageList.get(i).getPackageName());
        }

        while (true) {
            int innerChosenOption = sc.nextInt();
            if (innerChosenOption <= i - 1 && innerChosenOption >= 0) {
                packageList.remove(innerChosenOption);
                break;
            } else {
                System.out.println("Please enter a valid choice!");
            }
        }
        processMenuListToCSVFile();
    }

    /**
     * update a given promotion package based on user inputs
     */
    public void updatePromotionPackage() {
        int i;
        if (packageList.size() == 0) {
            System.out.println("There are no promotions to update!");
            return;
        }
        for (i = 0; i < packageList.size(); i++) {
            System.out.println(i + 1 + " " + packageList.get(i).getPackageName());
        }

        while (true) {
            int innerChosenOption = sc.nextInt();
            innerChosenOption--;
            if (innerChosenOption <= i - 1 && innerChosenOption >= 0) {
                packageList.get(innerChosenOption).updatePackage();
                break;
            } else {
                System.out.println("Please enter a valid choice!");
            }
        }
        processMenuListToCSVFile();

    }

    public static ArrayList<PromotionPackage> getPackageList() {
        return packageList;
    }


    public static void processMenuListToCSVFile() {
        try {
            // create a list of objects
            List<List<String>> records = new ArrayList<>();

            for (PromotionPackage promotionPackage : packageList) {
                List<String> tempList = new ArrayList<>();
                tempList.add(promotionPackage.getPackageName());
                tempList.add(promotionPackage.getPackageDescription());
                tempList.add(Double.toString(promotionPackage.getPackagePrice()));
                List<MenuItem> menuItemList = promotionPackage.getPromotionPackage();
                for (MenuItem menuItem : menuItemList) {
                    tempList.add(menuItem.getItemName());
                }
                records.add(tempList);
            }

            // create a writer

            FileWriter writer = new FileWriter("src/com/company/promotionPackage.csv", false);

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
