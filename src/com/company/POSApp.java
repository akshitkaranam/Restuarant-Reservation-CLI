package com.company;

import com.company.administrative.StaffList;
import com.company.restaurantessentials.*;

import java.util.*;

/**
 * This the class where the Main methods is present.
 */
public class POSApp {


    /**
     * Main method for the app to function
     * @param args args
     */
    public static void main(String[] args) {

        //Retrieve Saved Information from CSV Files
        RestaurantFunctions.retrieveRestaurantInformation();
        RestaurantFunctions.retrieveStaffInformation();
        RestaurantFunctions.retrieveMembershipInformation();
        RestaurantFunctions.retrieveMenuInformation();
        RestaurantFunctions.retrievePromotionPackageInformation();
        RestaurantFunctions.retrieveOrderReservationInformation();
        RestaurantFunctions.retrieveActiveOrderInformation();
        RestaurantFunctions.retrieveInvoicesInformation();

        RestaurantFunctions.setCurrentStaff(StaffList.getStaffList().get(0));


        int option;
        Scanner scanner = new Scanner(System.in);
        option = 1;
        while (option != 12) {
            System.out.println("Current Staff User is: " + RestaurantFunctions.getCurrentStaffUser().getName() + ", "
                    + RestaurantFunctions.getCurrentStaffUser().getJobRole());

            printMainMenu();
            option = scanner.nextInt();

            switch (option) {
                case 1 -> RestaurantFunctions.makeChangesToMenu();
                case 2 -> RestaurantFunctions.makeChangesToPackages();
                case 3 -> RestaurantFunctions.checkTableAvailability();
                case 4 -> RestaurantFunctions.addReservation();
                case 5 -> RestaurantFunctions.showListOfActiveReservationByDate();
                case 6 -> RestaurantFunctions.removeReservation();
                case 7 -> RestaurantFunctions.checkInCustomer();
                case 8 -> RestaurantFunctions.modifyActiveOrder();
                case 9 -> RestaurantFunctions.checkOutCustomer();
                case 10 -> RestaurantFunctions.printSalesByTimePeriod();
                case 11 -> RestaurantFunctions.changeStaffUser();
                case 12 -> System.out.println("Terminating the system! :)");
                default -> System.out.println("Please choose from options 1-12");
            }
        }
        scanner.close();
    }


    private static void printMainMenu() {
        System.out.println("Please select one of the options");
        System.out.println("---------------------------------");
        System.out.println("1: Create/Update/Remove Menu Item");
        System.out.println("2: Create/Update/Remove Promotion Package");
        System.out.println("3: Check table availability");
        System.out.println("4: Create new Reservation");
        System.out.println("5: Show Active Reservations by Date");
        System.out.println("6: Remove Reservation Booking");
        System.out.println("7: Check-in Customer");
        System.out.println("8: Modify Active Orders");
        System.out.println("9: Check-out Customer");
        System.out.println("10: Print Sales Report by Time Period");
        System.out.println("11: Change Staff User");
        System.out.println("12: Quit");
    }

}