package com.company.menuItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class MenuApp {
	
	public static void main(String[] args) {


		
		MenuList testMenu = new MenuList();
		PromotionPackageMenu testPromoMenu = new PromotionPackageMenu();
        try {
            // CSV file delimiter
            String DELIMITER = ";";

            // create a reader
            BufferedReader br = Files.newBufferedReader(Paths.get("src/com/company/menu.csv"));

            // read the file line by line
            String line;
            while ((line = br.readLine()) != null) {
                // convert line into tokens
                String[] tokens = line.split(DELIMITER);
                String menuItemName = tokens[0];
                String menuItemDescription = tokens[1];
                String menuItemPrice = tokens[2];
                String courseType = tokens[3];


                MenuItem tempMenuItem = new MenuItem(menuItemName, menuItemDescription
                        ,Double.parseDouble(menuItemPrice),courseType);
                MenuList.getmItemList().add(tempMenuItem);
            }
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        for(int i =0;i<MenuList.getmItemList().size();i++){
            System.out.println(MenuList.getmItemList().get(i));
        }
		
		
		
		
        int option =1;
        Scanner sc = new Scanner(System.in);

        do {
        	System.out.println("Choose an option:\n"
                    + "================================\n"
                    + "|1. Create a new menu item |\n"
                    + "|2. Update Menu Item|\n"
                    + "|3. Remove Menu Item |\n"
                    + "|4. Display Menu|\n"
                    + "|5. Quit|\n"
                    + "==================================");
            option = sc.nextInt();
            switch (option) {
                case 1:
                    testMenu.createMenuItem();
                    System.out.println("A new item has been created");
                    break;
                case 2:
                    testMenu.updateMenuItem();
                    break;
                case 3:
                    testMenu.removeMenuItem();
                    break;
                case 4:
                    testMenu.displayMenu();
                    break;
                case 5:
                	System.out.println("Exiting MenuApp");
                	break;
                case 6:
                	testPromoMenu.addPromotionPackage();
                	break;
                case 7:
                	testPromoMenu.removePromotionPackage();
                	break;
                case 8:
                	testPromoMenu.displayPackageMenu();
                	break;
                case 9:
                	testPromoMenu.updatePromotionPackage();
                	break;
                default:
                    System.out.println("Choose option (1-5):");
                    break;
            }

        } while (option !=5);

        sc.close();

    }
    
}

