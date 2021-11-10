package com.company;

import com.company.administrative.Customer;
import com.company.administrative.MembershipList;
import com.company.administrative.Staff;
import com.company.administrative.StaffList;
import com.company.menuItem.MenuItem;
import com.company.menuItem.MenuList;
import com.company.menuItem.PromotionPackage;
import com.company.menuItem.PromotionPackageMenu;
import com.company.restaurantfunctions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;

public class POSApp {

    static Staff currentStaffUser;

    public static void main(String[] args) {

        //Tables
        Restaurant.addTable(1, 2);
        Restaurant.addTable(2, 2);
        Restaurant.addTable(3, 4);
        Restaurant.addTable(4, 4);
        Restaurant.addTable(5, 6);
        Restaurant.addTable(6, 6);
        Restaurant.addTable(7, 8);
        Restaurant.addTable(8, 8);
        Restaurant.addTable(9, 10);
        Restaurant.addTable(10, 10);

        //Membership List
        MembershipList.addMember(new Customer("Tom", "12345678"));
        MembershipList.addMember(new Customer("Bob", "23456789"));
        MembershipList.addMember(new Customer("David", "34567891"));
        MembershipList.addMember(new Customer("Donald", "45678901"));
        MembershipList.addMember(new Customer("Joe", "56789012"));
        MembershipList.addMember(new Customer("Chris", "67890123"));
        MembershipList.addMember(new Customer("Sally", "78901234"));

        //StaffList
        StaffList.addUser("Christina", "000001", Staff.JobRole.MANAGER);
        StaffList.addUser("Thomas", "000002", Staff.JobRole.WAITER);
        StaffList.addUser("Lisa", "000003", Staff.JobRole.WAITER);
        StaffList.addUser("Jessie", "000004", Staff.JobRole.WAITER);
        StaffList.addUser("Fred", "000005", Staff.JobRole.WAITER);
        StaffList.addUser("Wayne", "000006", Staff.JobRole.WAITER);
        currentStaffUser = StaffList.getStaffList().get(0); //Default is the manager

        //Retrieve Saved Information from CSV Files
        retrieveMenuInformation();
        retrieveOrderReservationInformation();
        retrieveActiveOrderInformation();
        retrieveInvoicesInformation();

        int option;
        Scanner scanner = new Scanner(System.in);
        option = 1;
        while (option != 12) {
            System.out.println("Current Staff User is: " + currentStaffUser.getName() + ", "
                    + currentStaffUser.getJobRole());

            printMainMenu();
            option = scanner.nextInt();
            switch (option) {

                case 1:
                    makeChangesToMenu();
                    break;
                case 2:
                    makeChangesToPackages();
                    break;
                case 3:
                    addReservation();
                    break;
                case 4:
                    showListOfReservationByDate();
                    break;
                case 5:
                    removeReservation();
                    break;
                case 6:
                    checkTableAvailability();
                    break;
                case 7:
                    checkInCustomer();
                    break;
                case 8:
                    addItemsToOrder();
                    break;
                case 9:
                    checkOutCustomer();
                    break;
                case 10:
                    printSalesByTimePeriod();
                    break;
                case 11:
                    changeStaffUser();
                    break;
                case 12:
                    System.out.println("Terminating the system");
                    break;
                default:
                    System.out.println("Please choose from options 1-12");
                    break;
            }
        }
        scanner.close();
    }


    public static void printMainMenu() {
        System.out.println("Please select one of the options");
        System.out.println("---------------------------------");
        System.out.println("1: Create/Update/Remove Menu Item");
        System.out.println("2: Create/Update/Remove Promotion Package");
        System.out.println("3: Create new Reservation");
        System.out.println("4: Show Active Reservations by Date");
        System.out.println("5: Remove Reservation Booking");
        System.out.println("6: Check table availability");
        System.out.println("7: Check-in Customer");
        System.out.println("8: Modify Active Orders");
        System.out.println("9: Check-out Customer");
        System.out.println("10: Print Sales Report by Time Period");
        System.out.println("11: Change Staff User");
        System.out.println("12: Quit");
    }


    public static void makeChangesToMenu() {
        int option;
        Scanner sc = new Scanner(System.in);
        MenuList testMenu = new MenuList();
        PromotionPackageMenu testPromoMenu = new PromotionPackageMenu();

        do {
            System.out.println("MAKE CHANGES TO MENU");
            System.out.println("""
                    Choose an option:
                    ================================
                    |1. Create a new menu item |
                    |2. Update Menu Item|
                    |3. Remove Menu Item |
                    |4. Display Menu|
                    |5. Quit from Menu Changes|
                    ==================================""");
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

        } while (option != 5);

    }


    public static void makeChangesToPackages() {
        int option;
        Scanner sc = new Scanner(System.in);

        PromotionPackageMenu testPromoMenu = new PromotionPackageMenu();

        do {
            System.out.println("---------------------------");
            System.out.println("MAKE CHANGES TO Packages");
            System.out.println("""
                    Choose an option:
                    ================================
                    |1. Create a Promotion Package |
                    |2. Update a Promotion Package |
                    |3. Remove a Promotion Package |
                    |4. Display Promotion Package Menu|
                    |5. Quit from Menu Changes|
                    ==================================""");
            option = sc.nextInt();
            switch (option) {
                case 1:
                    testPromoMenu.addPromotionPackage();
                    break;
                case 2:
                    testPromoMenu.updatePromotionPackage();
                    break;
                case 3:
                    testPromoMenu.removePromotionPackage();
                    break;
                case 4:
                    testPromoMenu.displayPackageMenu();
                    break;
                case 5:
                    System.out.println("Exiting MenuApp");
                    break;
                default:
                    System.out.println("Choose option (1-5):");
                    break;
            }

        } while (option != 5);

    }

    public static void addReservation() {
        Scanner scanner = new Scanner(System.in);
        Set<LocalTime> availableSlotsSet = new LinkedHashSet<>();
        LocalDate date;
        LocalTime time;
        int groupSize;
        int durationInMinutes;
        boolean reservationIsPossible;


        while (true) {
            try {
                System.out.println("Enter the date you wish to reserve in the format YYYY-MM-DD");
                String dateEntered = scanner.next();
                date = LocalDate.parse(dateEntered);

                if (date.isBefore(LocalDate.now())) {
                    System.out.println("You cannot add Reservation to the past! Please enter a valid date!");
                    continue;
                }

                if (date.isAfter(LocalDate.now().plusDays(14))) {
                    System.out.println("You can only add reservations for the next 14 days!");
                    continue;
                }
                break;

            } catch (DateTimeParseException e) {
                System.out.println("INCORRECT ENTRY!");
                System.out.println("Please enter in the correct format: YYYY-MM-DD");
            }
        }


        while (true) {
            try {
                System.out.println("Please enter your group size: ");
                scanner.nextLine();
                groupSize = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("INCORRECT ENTRY!");
                System.out.println("Please enter only integer values!");
            }
        }


        for (Map.Entry<Integer, Table> tableEntry : Restaurant.getTableList().entrySet()) {
            if (tableEntry.getValue().getNumberOfSeats() >= groupSize) {
                if (!tableEntry.getValue().getTableDateSlotsList().containsKey(date)) {
                    tableEntry.getValue().getTableDateSlotsList().put(date, new TableDateSlots(date));

                }

                Map<LocalTime, Order> tempSlotList = tableEntry.getValue().getTableDateSlotsList().get(date).getSlots();

                for (Map.Entry<LocalTime, Order> timeReservationEntry : tempSlotList.entrySet()) {

                    if (timeReservationEntry.getValue() == null) {
                        availableSlotsSet.add(timeReservationEntry.getKey());
                    }
                }
            }
        }

        if (availableSlotsSet.isEmpty()) {
            System.out.println("Sorry there are no tables available on this date: " + date + " for a group size of " + groupSize);
            System.out.println();
            return;
        }

        while (true) {
            try {
                System.out.println("Please enter the time you want to book:  e.g 09:30 ");
                String timeEntered = scanner.next();
                timeEntered = timeEntered + ":00";
                time = LocalTime.parse(timeEntered);

                if (time.getMinute() != 0 && time.getMinute() != 30) {
                    System.out.println("You can only reserve slots in increments of 30 minutes. Please try again!");
                    continue;
                }

                if (LocalDateTime.of(date, time).isBefore(LocalDateTime.now().minusMinutes(15))) {
                    System.out.println("You cannot reserve slots in the past. Please try again!");
                } else {
                    break;
                }


            } catch (DateTimeParseException e) {
                System.out.println("INCORRECT ENTRY!");
                System.out.println("Please enter in the correct format: hh:mm (i.e. 09:30)");
            }
        }

        while (true) {
            try {
                System.out.println("Please enter the the duration you want to reserve the table in hours (e.g. 1.5 or 2");
                scanner.nextLine();
                double duration = scanner.nextDouble();

                if (duration % 0.5 != 0) {
                    System.out.println("Please only enter duration with 0.5 increments (i.e 1/1.5/2.5)");
                    continue;
                }

                durationInMinutes = (int) (60 * duration);
                reservationIsPossible = false;
                break;
            } catch (InputMismatchException e) {
                System.out.println("INCORRECT ENTRY!");
                System.out.println("Please enter only decimal values!");
            }
        }

        for (Map.Entry<Integer, Table> tableEntry : Restaurant.getTableList().entrySet()) {
            if (tableEntry.getValue().getNumberOfSeats() >= groupSize) {
                if (tableEntry.getValue().getTableDateSlotsList().get(date).getSlots().get(time) == null) {
                    reservationIsPossible = true;
                    for (int i = 30; i < durationInMinutes; i += 30) {
                        if (tableEntry.getValue().getTableDateSlotsList().get(date).getSlots().get(time.plusMinutes(i)) != null) {
                            reservationIsPossible = false;
                            break;
                        }
                    }
                }
            }

            if (!reservationIsPossible) {
                continue;
            }

            Customer thisCustomer;
            String name;
            String contactNumber;
            System.out.println("Are you a member? y/n");
            String isMemberInput = scanner.next();
            if (isMemberInput.equalsIgnoreCase("y")) {
                System.out.println("Please enter your contact number");
                contactNumber = scanner.next();

                if (MembershipList.getMembersList().containsKey(contactNumber)) {
                    name = MembershipList.getMembersList().get(contactNumber).getName();
                    System.out.println("Confirm your name: "
                            + name + " y/n");
                    String nameIsCorrect = scanner.next();
                    if (nameIsCorrect.equalsIgnoreCase("y")) {
                        thisCustomer = MembershipList.getMembersList().get(contactNumber);
                    } else {
                        System.out.println("Sorry you are not a member!");
                        System.out.println("Please enter your name: ");
                        name = scanner.next();
                        thisCustomer = new Customer(name, contactNumber);
                    }

                } else {
                    System.out.println("Sorry you are not a member!");
                    System.out.println("Please enter your name: ");
                    name = scanner.next();
                    thisCustomer = new Customer(name, contactNumber);
                }

            } else {
                System.out.println("Please enter your name: ");
                name = scanner.next();
                System.out.println("Please enter your contact number");
                contactNumber = scanner.next();
                thisCustomer = new Customer(name, contactNumber);
            }

            Order thisOrder = new Order(thisCustomer, groupSize, tableEntry.getValue().getTableNumber()
                    , date, time, time.plusMinutes(durationInMinutes));

            tableEntry.getValue().getTableDateSlotsList().get(date).reserveSlot(time, thisOrder, durationInMinutes);

            System.out.println("Reservation is confirmed for " + name + " on " + date + " at " + time + " to " + time.plusMinutes(durationInMinutes));
            System.out.println();
            Restaurant.processActiveReservationsToCSV();
            return;

        }
        System.out.println("Sorry Reservation isn't possible for this date, time and duration");
        System.out.println();
    }

    public static void showListOfReservationByDate() {
        LocalDate date;
        Scanner scanner = new Scanner(System.in);


        while (true) {
            try {
                System.out.println("Enter the date you wish to get the list of reservations in the format YYYY-MM-DD");
                String dateEntered = scanner.next();
                date = LocalDate.parse(dateEntered);
                if (date.isBefore(LocalDate.now())) {
                    System.out.println("You cannot access Reservation of the past! Please enter a valid date!");
                    continue;
                }

                if (date.isAfter(LocalDate.now().plusDays(14))) {
                    System.out.println("You can only access reservations for the next 14 days!");
                    continue;
                }
                break;

            } catch (DateTimeParseException e) {
                System.out.println("INCORRECT ENTRY!");
                System.out.println("Please enter the date in the correct format: YYYY-MM-DD");
                System.out.println();
            }
        }


        Set<Order> setOfAllReservation = new HashSet<>();


        for (Table table : Restaurant.getTableList().values()) {
            List<Order> reservations = table.getOrderReservationsByDateForTable(date);
            setOfAllReservation.addAll(reservations);
        }

        List<Order> listOfAllOrderReservations = new ArrayList<>(setOfAllReservation);
        if (listOfAllOrderReservations.isEmpty()) {
            System.out.println("There are currently no reservations on this date!");
        }

        for (Order order : listOfAllOrderReservations) {
            if (order == null) {
                continue;
            }
            System.out.println(order);
        }
        System.out.println();
    }

    private static void removeReservation() {


        LocalDate date;
        Scanner scanner = new Scanner(System.in);


        while (true) {
            try {
                System.out.println("Enter the date you wish to remove reservations in the format YYYY-MM-DD");
                String dateEntered = scanner.next();
                date = LocalDate.parse(dateEntered);

                if (date.isBefore(LocalDate.now())) {
                    System.out.println("You cannot add Reservation to the past! Please enter a valid date!");
                    continue;
                }

                if (date.isAfter(LocalDate.now().plusDays(14))) {
                    System.out.println("You can only add reservations for the next 14 days!");
                    continue;
                }

                break;
            } catch (DateTimeParseException e) {
                System.out.println("INCORRECT ENTRY!");
                System.out.println("Please enter in the correct format: YYYY-MM-DD");
                System.out.println();
            }
        }


        Set<Order> setOfAllReservation = new HashSet<>();
        List<Integer> availableIndex = new ArrayList<>();


        for (Table table : Restaurant.getTableList().values()) {
            List<Order> reservations = table.getOrderReservationsByDateForTable(date);
            setOfAllReservation.addAll(reservations);
        }

        List<Order> listOfAllOrderReservations = new ArrayList<>(setOfAllReservation);
        int optionChosen;

        if (listOfAllOrderReservations.isEmpty()) {
            System.out.println("There are no reservations available!");
            return;
        }
        for (int i = 0; i < listOfAllOrderReservations.size(); i++) {
            if (listOfAllOrderReservations.get(i) == null) {
                continue;
            }
            availableIndex.add(i);
            System.out.println(i + ": " + listOfAllOrderReservations.get(i));
        }

        while (true) {
            optionChosen = scanner.nextInt();
            if (availableIndex.contains(optionChosen)) {
                break;
            } else {
                System.out.println("Please enter a valid choice!");
            }
        }

        Order orderToRemove = listOfAllOrderReservations.get(optionChosen);
        LocalTime reservationStartTime = orderToRemove.getReservationStartTime();
        LocalTime reservationEndTime = orderToRemove.getReservationEndTime();
        Table tableOfOrder = Restaurant.getTableList().get(orderToRemove.getTableNumber());
        TableDateSlots todaySlotsForThisTable = tableOfOrder.getTableDateSlotsList().get(LocalDate.now());

        LocalTime time = reservationStartTime;
        while (time.isBefore(reservationEndTime)) {
            todaySlotsForThisTable.getSlots().put(time, null);
            time = time.plusMinutes(30);
        }
        Restaurant.processActiveReservationsToCSV();
        System.out.println("Successfully removed reservation of" + orderToRemove.getCustomer().getName()
                + " from " + orderToRemove.getReservationStartTime() + " to " + orderToRemove.getReservationEndTime());
        System.out.println();
    }

    private static void checkTableAvailability() {

        LocalDate date;
        LocalTime time;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Enter the date you wish to check thr table availability");
                String dateEntered = scanner.next();
                date = LocalDate.parse(dateEntered);

                if (date.isBefore(LocalDate.now())) {
                    System.out.println("You cannot check for table availability in the past! Please enter a valid date!");
                    continue;
                }

                if (date.isAfter(LocalDate.now().plusDays(14))) {
                    System.out.println("You can only check for available tables for the next 14 days!");
                    continue;
                }

                break;
            } catch (DateTimeParseException e) {
                System.out.println("INCORRECT ENTRY!");
                System.out.println("Please enter in the correct format: YYYY-MM-DD");
                System.out.println();
            }
        }

        while (true) {
            try {
                System.out.println("Please enter the time you want to check:  e.g 09:30 ");
                String timeEntered = scanner.next();
                timeEntered = timeEntered + ":00";
                time = LocalTime.parse(timeEntered);

                if (time.getMinute() != 0 && time.getMinute() != 30) {
                    System.out.println("You can only check slots in increments of 30 minutes. Please try again!");
                    continue;
                }

                if (LocalDateTime.of(date, time).isBefore(LocalDateTime.now().minusMinutes(15))) {
                    System.out.println("You cannot check for slots in the past. Please try again!");
                } else {
                    break;
                }


            } catch (DateTimeParseException e) {
                System.out.println("INCORRECT ENTRY!");
                System.out.println("Please enter in the correct format: hh:mm (i.e. 09:30)");
            }
        }
        boolean allFull = true;
        System.out.println("These are the empty tables at this time");
        for (Table table : Restaurant.getTableList().values()) {
            if(!table.getTableDateSlotsList().containsKey(date)) {
                table.getTableDateSlotsList().put(date, new TableDateSlots(date));
            }

            if (table.getTableDateSlotsList().get(date).getSlots().get(time) == null) {
                System.out.print(table.getTableNumber() + ", ");
                allFull = false;
            }
        }
        System.out.println();

        if (allFull) {
            System.out.println("All the tables are full at this time!");
        }
        System.out.println();

    }

    private static void checkInCustomer() {
        LocalDate dateNow = LocalDate.now();
        LocalTime timeNow = LocalTime.now();
        Collection<Table> tableList = Restaurant.getTableList().values();
        Set<Order> setOfAllReservation = new HashSet<>();
        Scanner scanner = new Scanner(System.in);

        for (Table table : tableList) {
            List<Order> reservations = table.getOrderReservationsByDateForTable(dateNow);
            setOfAllReservation.addAll(reservations);
        }

        System.out.println("Choose the from the following reservation to check-in: ");
        List<Order> orderOfAllReservationForToday = new ArrayList<>(setOfAllReservation);
        boolean inactiveAvailable = false;
        List<Integer> availableIndex = new ArrayList<>();
        for (int i = 0; i < orderOfAllReservationForToday.size(); i++) {
            Order thisOrder = orderOfAllReservationForToday.get(i);

            if (thisOrder != null) {
                //Remove Reservation if time now is 30 minutes after the reservation start time
                if (thisOrder.getReservationStartTime().plusMinutes(30).isBefore(LocalTime.now())) {

                    LocalTime reservationStartTime = thisOrder.getReservationStartTime();
                    LocalTime reservationEndTime = thisOrder.getReservationEndTime();
                    Table tableOfOrder = Restaurant.getTableList().get(thisOrder.getTableNumber());
                    TableDateSlots todaySlotsForThisTable = tableOfOrder.getTableDateSlotsList().get(LocalDate.now());
                    LocalTime time = reservationStartTime;
                    while (time.isBefore(reservationEndTime)) {
                        todaySlotsForThisTable.getSlots().put(time, null);
                        time = time.plusMinutes(30);
                    }
                    continue;
                }

                if (!thisOrder.isOrderIsActive()) {
                    inactiveAvailable = true;
                    LocalTime reservationStartTime = thisOrder.getReservationStartTime().minusMinutes(20);
                    LocalTime reservationEndTime = thisOrder.getReservationEndTime().minusMinutes(30);

                    if (timeNow.isAfter(reservationStartTime) && timeNow.isBefore(reservationEndTime)) {
                        System.out.println(i + ": " + thisOrder.getCustomer().getName() + " "
                                + thisOrder.getCustomer().getContactNumber());
                        availableIndex.add(i);

                    }
                }

            }

        }

        if (!inactiveAvailable) {
            System.out.println("There are no reservations to currently check-in at this time!");
            return;
        }

        int optionSelected;

        while (true) {
            optionSelected = scanner.nextInt();
            if (availableIndex.contains(optionSelected)) {
                break;
            } else {
                System.out.println("Please enter a valid choice!");
            }
        }

        Order relevantOrder = orderOfAllReservationForToday.get(optionSelected);
        relevantOrder.setOrderIsActive(true);
        Restaurant.addActiveOrder(relevantOrder);
        Restaurant.processActiveOrderToCSV();
        System.out.println("Successfully checked-in " + relevantOrder.getCustomer().getName() + " with group size of "
                + relevantOrder.getGroupSize() + "! Please escort them to table number: " + relevantOrder.getTableNumber());
        System.out.println();
    }

    private static void addItemsToOrder() {
        List<Order> activeOrders = Restaurant.getActiveOrders();
        Scanner sc = new Scanner(System.in);
        int i = 0;
        if (activeOrders.isEmpty()) {
            System.out.println("There are currently no active orders!");
            return;
        }
        System.out.println("Please select from the following orders to modify: ");

        for (Order order : activeOrders) {
            System.out.println(i++ + ": " + order.getCustomer().getName() + ". Table Number: " + order.getTableNumber());
        }
        int chosenOption = sc.nextInt();
        Order relevantOrder = activeOrders.get(chosenOption);
        int option;
        do {
            System.out.println("MAKE CHANGES TO Order to table number: " + relevantOrder.getTableNumber());
            System.out.println("""
                    Choose an option:
                    ================================
                    |1. Add MenuItem |
                    |2. Modify Item Quantity
                    |3. Remove MenuItem |
                    |4. Show Added MenuItems and Quantity|
                    |5. Add Promotion Package |
                    |6. Modify Package Quantity
                    |7. Remove Promotion Package |
                    |8. Show Added Promotion Package and Quantity|
                                        
                    |5. Quit Making Changes to Order
                    ==================================""");
            option = sc.nextInt();
            switch (option) {
                case 1:
                    int innerChosenOption;
                    int j;
                    for (j = 0; j < MenuList.getmItemList().size(); j++) {
                        System.out.println(j + " " + MenuList.getmItemList().get(j));
                    }

                    while (true) {
                        innerChosenOption = sc.nextInt();
                        if (innerChosenOption <= j - 1 && innerChosenOption >= 0) {
                            break;
                        } else {
                            System.out.println("Please enter a valid choice!");
                        }
                    }

                    System.out.println("Enter Quantity: ");
                    int quantity = sc.nextInt();
                    relevantOrder.addMenuItemToOrder(MenuList.getmItemList().get(innerChosenOption), quantity);
                    break;

                case 2:
                    List<MenuItem> menuItemInOrder = new ArrayList<>(relevantOrder.getItemsOrderedList().keySet());
                    j = 0;
                    for (MenuItem menuItem : menuItemInOrder) {
                        System.out.println(j++ + ": " + menuItem + ", Quantity: "
                                + relevantOrder.getItemsOrderedList().get(menuItem));
                    }

                    while (true) {
                        innerChosenOption = sc.nextInt();
                        if (innerChosenOption <= j - 1 && innerChosenOption >= 0) {
                            break;
                        } else {
                            System.out.println("Please enter a valid choice!");
                        }
                    }

                    MenuItem chosenMenuItem = menuItemInOrder.get(innerChosenOption);
                    System.out.println("Please enter new quantity: ");
                    quantity = sc.nextInt();
                    relevantOrder.editQuantity(chosenMenuItem, quantity);
                    break;

                case 3:
                    menuItemInOrder = new ArrayList<>(relevantOrder.getItemsOrderedList().keySet());
                    j = 0;
                    for (MenuItem menuItem : menuItemInOrder) {
                        System.out.println(j++ + ": " + menuItem + ", Quantity: "
                                + relevantOrder.getItemsOrderedList().get(menuItem));
                    }
                    while (true) {
                        innerChosenOption = sc.nextInt();
                        if (innerChosenOption <= j - 1 && innerChosenOption >= 0) {
                            break;
                        } else {
                            System.out.println("Please enter a valid choice!");
                        }
                    }
                    chosenMenuItem = menuItemInOrder.get(innerChosenOption);
                    relevantOrder.deleteMenuItem(chosenMenuItem);
                    break;

                case 4:
                    j = 0;
                    menuItemInOrder = new ArrayList<>(relevantOrder.getItemsOrderedList().keySet());
                    for (MenuItem menuItem : menuItemInOrder) {
                        System.out.println(j++ + ": " + menuItem + ", Quantity: "
                                + relevantOrder.getItemsOrderedList().get(menuItem));
                    }
                    break;


                case 5:
                    ;
                    for (j = 0; j < PromotionPackageMenu.getPackageList().size(); j++) {
                        System.out.println(j + " " + PromotionPackageMenu.getPackageList().get(j));
                    }

                    while (true) {
                        innerChosenOption = sc.nextInt();
                        if (innerChosenOption <= j - 1 && innerChosenOption >= 0) {
                            break;
                        } else {
                            System.out.println("Please enter a valid choice!");
                        }
                    }

                    System.out.println("Enter Quantity: ");
                    quantity = sc.nextInt();
                    relevantOrder.addPromotionPackageToOrder(PromotionPackageMenu.
                            getPackageList().get(innerChosenOption), quantity);
                    break;


                case 6:
                    List<PromotionPackage> promotionPackageList
                            = new ArrayList<>(relevantOrder.getPromotionPackageOrderedList().keySet());
                    j = 0;
                    for (PromotionPackage promoPack : promotionPackageList) {
                        System.out.println(j++ + ": " + promoPack + ", Quantity: "
                                + relevantOrder.getPromotionPackageOrderedList().get(promoPack));
                    }

                    while (true) {
                        innerChosenOption = sc.nextInt();
                        if (innerChosenOption <= j - 1 && innerChosenOption >= 0) {
                            break;
                        } else {
                            System.out.println("Please enter a valid choice!");
                        }
                    }

                    PromotionPackage chosenPromoPack = promotionPackageList.get(innerChosenOption);
                    System.out.println("Please enter new quantity: ");
                    quantity = sc.nextInt();
                    relevantOrder.editQuantity(chosenPromoPack, quantity);
                    break;

                case 7:
                    promotionPackageList
                            = new ArrayList<>(relevantOrder.getPromotionPackageOrderedList().keySet());
                    j = 0;
                    for (PromotionPackage promoPack : promotionPackageList) {
                        System.out.println(j++ + ": " + promoPack + ", Quantity: "
                                + relevantOrder.getPromotionPackageOrderedList().get(promoPack));
                    }
                    while (true) {
                        innerChosenOption = sc.nextInt();
                        if (innerChosenOption <= j - 1 && innerChosenOption >= 0) {
                            break;
                        } else {
                            System.out.println("Please enter a valid choice!");
                        }
                    }

                    chosenPromoPack = promotionPackageList.get(innerChosenOption);
                    relevantOrder.deletePromotionPackage(chosenPromoPack);
                    break;

                case 8:
                    j = 0;
                    promotionPackageList
                            = new ArrayList<>(relevantOrder.getPromotionPackageOrderedList().keySet());
                    menuItemInOrder = new ArrayList<>(relevantOrder.getItemsOrderedList().keySet());
                    for (PromotionPackage promoPack : promotionPackageList) {
                        System.out.println(j++ + ": " + promoPack + ", Quantity: "
                                + relevantOrder.getItemsOrderedList().get(promoPack));
                    }
                    break;

                case 9:
                    System.out.println("Quitting updating the menu");
                    break;

                default:
                    System.out.println("Choose option (1-5):");
                    break;
            }

        } while (option != 9);

        Restaurant.processActiveOrderToCSV();
        System.out.println();
    }


    private static void checkOutCustomer() {

        List<Order> activeOrders = Restaurant.getActiveOrders();
        int optionChosen;
        if (activeOrders.isEmpty()) {
            System.out.println("There are currently no active orders!");
            return;
        }
        System.out.println("Choose the from the following reservation to check-out: ");
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        for (Order order : activeOrders) {
            System.out.println(i++ + ": " + order.getCustomer().getName() + ". Table Number: " + order.getTableNumber());
        }

        while (true) {
            optionChosen = scanner.nextInt();
            if (optionChosen <= i - 1 && optionChosen >= 0) {
                break;
            } else {
                System.out.println("Please enter a valid choice!");
            }
        }

        Order orderToCheckOut = activeOrders.get(optionChosen);
        orderToCheckOut.setStaff(currentStaffUser);
        Invoice thisOrderInvoice = new Invoice(orderToCheckOut);
        thisOrderInvoice.generateReceipt();
        activeOrders.remove(optionChosen);
//        Restaurant.processActiveOrderToCSV();


        //Remove Reservation
        LocalTime reservationStartTime = orderToCheckOut.getReservationStartTime();
        LocalTime reservationEndTime = orderToCheckOut.getReservationEndTime();
        Table tableOfOrder = Restaurant.getTableList().get(orderToCheckOut.getTableNumber());
        TableDateSlots todaySlotsForThisTable = tableOfOrder.getTableDateSlotsList().get(LocalDate.now());

        LocalTime time = reservationStartTime;
        while (time.isBefore(reservationEndTime)) {
            todaySlotsForThisTable.getSlots().put(time, null);
            time = time.plusMinutes(30);
        }
        System.out.println();
        Restaurant.processActiveReservationsToCSV();
        InvoiceList.processInvoiceListToCSVFile();
    }

    private static void printSalesByTimePeriod() {
        int option;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("""
                    Choose an option:
                    ================================
                    |1. Get Report By Day |
                    |2. Get Report By Month|
                    |3. Quit|
                    ==================================""");
            option = sc.nextInt();
            switch (option) {
                case 1:
                    System.out.println("Enter the desired date in the format YYYY-MM-DD");
                    String dateEntered = sc.next();
                    LocalDate date = LocalDate.parse(dateEntered);
                    InvoiceList.salesReportByDay(date);
                    break;
                case 2:
                    System.out.println("Enter the date month in the format MM (i.e 12) ");
                    int monthEntered = sc.nextInt();
                    Month month = Month.of(monthEntered);
                    InvoiceList.salesReportByMonth(month);
                    break;
                case 3:
                    System.out.println("Quitting, going back to Main Menu!");
                    break;

                default:
                    System.out.println("Choose option (1-3):");
                    break;
            }

        } while (option != 3);
        System.out.println();
    }


    private static void changeStaffUser() {
        List<Staff> staffList = StaffList.getStaffList();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Current Staff User Logged-in: " + currentStaffUser.getName() + ": "
                + currentStaffUser.getJobRole().name());

        System.out.println("Please choose from the following: ");
        for (int i = 0; i < staffList.size(); i++) {
            System.out.println(i + ": " + staffList.get(i).getName() + ", " + staffList.get(i).getJobRole());
        }
        int chosenOption = scanner.nextInt();
        currentStaffUser = staffList.get(chosenOption);
        System.out.println();
    }

    private static void retrieveMenuInformation() {

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
                        , Double.parseDouble(menuItemPrice), courseType);
                MenuList.getmItemList().add(tempMenuItem);
            }
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private static void retrieveOrderReservationInformation() {

        try {
            // CSV file delimiter
            String DELIMITER = ";";

            // create a reader
            BufferedReader br = Files.newBufferedReader(Paths.get("src/com/company/orderReservations.csv"));

            // read the file line by line
            String line;
            while ((line = br.readLine()) != null) {
                // convert line into tokens
                String[] tokens = line.split(DELIMITER);
                String customerName = tokens[0];
                String customerContactNumber = tokens[1];
                String date = tokens[2];
                String tableNumber = tokens[3];
                String groupSize = tokens[4];
                String reservationStartTime = tokens[5];
                String reservationEndTime = tokens[6];
                Customer thisCustomer;
                Order thisOrder;

                if (MembershipList.getMembersList().containsKey(customerContactNumber) &&
                        MembershipList.getMembersList().get(customerContactNumber).getName().equals(customerName)) {
                    thisCustomer = MembershipList.getMembersList().get(customerContactNumber);
                } else {
                    thisCustomer = new Customer(customerName, customerContactNumber);
                }

                Table requiredTable = Restaurant.getTableList().get(Integer.parseInt(tableNumber));

                if (!requiredTable.getTableDateSlotsList().containsKey(LocalDate.parse(date))) {
                    requiredTable.getTableDateSlotsList().put(LocalDate.parse(date), new TableDateSlots(LocalDate.parse(date)));
                }


                TableDateSlots requiredDateSlots = requiredTable.getTableDateSlotsList().get(LocalDate.parse(date));
                thisOrder = new Order(thisCustomer, Integer.parseInt(groupSize), Integer.parseInt(tableNumber)
                        , LocalDate.parse(date), LocalTime.parse(reservationStartTime), LocalTime.parse(reservationEndTime));
                int duration = (int) Duration.between(LocalTime.parse(reservationStartTime), LocalTime.parse(reservationEndTime)).toMinutes();
                requiredDateSlots.reserveSlot(LocalTime.parse(reservationStartTime), thisOrder, duration);
            }

            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private static void retrieveActiveOrderInformation() {

        try {
            // CSV file delimiter
            String DELIMITER = ";";

            // create a reader
            BufferedReader br = Files.newBufferedReader(Paths.get("src/com/company/activeOrders.csv"));

            // read the file line by line
            String line;
            while ((line = br.readLine()) != null) {
                // convert line into tokens
                String[] tokens = line.split(DELIMITER);
                String orderID = tokens[0];
                String listOfMenuItems = tokens[1];
                Order thisOrder = null;
                String name;
                int quantity;

                for (Table table : Restaurant.getTableList().values()) {
                    List<Order> ordersToday = table.getOrderReservationsByDateForTable(LocalDate.now());
                    for (Order order : ordersToday) {
                        if (order == null) {
                            continue;
                        }
                        if (order.getOrderNumber() == Integer.parseInt(orderID)) {
                            thisOrder = order;
                            break;
                        }
                    }
                }
                Restaurant.addActiveOrder(thisOrder);
                listOfMenuItems = listOfMenuItems.substring(1, listOfMenuItems.length() - 1);
                String[] tokens2 = listOfMenuItems.split(", ");
                System.out.println(Arrays.toString(tokens2));

                for (String token2 : tokens2) {
                    String[] tokens3 = token2.split("=");
                    name = tokens3[0];
                    quantity = Integer.parseInt(tokens3[1]);

                    for (int i = 0; i < MenuList.getmItemList().size(); i++) {
                        if (MenuList.getmItemList().get(i).getItemName().equals(name)) {
                            thisOrder.addMenuItemToOrder(MenuList.getmItemList().get(i), quantity);
                            break;
                        }
                    }
                }

                thisOrder.setOrderIsActive(true);
            }
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private static void retrieveInvoicesInformation() {

        try {
            // CSV file delimiter
            String DELIMITER = ";";

            // create a reader
            BufferedReader br = Files.newBufferedReader(Paths.get("src/com/company/orderInvoices.csv"));

            // read the file line by line
            String line;
            while ((line = br.readLine()) != null) {

                String itemName;
                int itemQuantity;
                Customer thisCustomer;

                // convert line into tokens
                String[] tokens = line.split(DELIMITER);
                LocalDateTime date = LocalDateTime.parse(tokens[0]);

                //ORDER
                LocalDate orderDate = LocalDate.parse(tokens[1]);
                int orderNumber = Integer.parseInt(tokens[2]);
                String customerName = tokens[3];
                String customerContactNumber = tokens[4];
                int groupSize = Integer.parseInt(tokens[5]);
                LocalTime reservationStartTime = LocalTime.parse(tokens[6]);
                LocalTime reservationEndTime = LocalTime.parse(tokens[7]);
                String listOfMenuItems = tokens[8];
                int tableNumber = Integer.parseInt(tokens[9]);
                String staffNameString = tokens[10];
                Staff staffName = null;
                for (Staff staff : StaffList.getStaffList()) {
                    if (staff.getName().equals(staffNameString)) {
                        staffName = staff;
                    }
                }

                listOfMenuItems = listOfMenuItems.substring(1, listOfMenuItems.length() - 1);
                String[] tokens2 = listOfMenuItems.split(", ");


                if (MembershipList.getMembersList().containsKey(customerContactNumber) &&
                        MembershipList.getMembersList().get(customerContactNumber).getName().equals(customerName)) {
                    thisCustomer = MembershipList.getMembersList().get(customerContactNumber);
                } else {
                    thisCustomer = new Customer(customerName, customerContactNumber);
                }

                Order thisOrder = new Order(thisCustomer, orderNumber, groupSize, tableNumber, orderDate
                        , reservationStartTime, reservationEndTime, false, staffName, false);

                for (String token2 : tokens2) {
                    String[] tokens3 = token2.split("=");
                    itemName = tokens3[0];
                    itemQuantity = Integer.parseInt(tokens3[1]);

                    for (int i = 0; i < MenuList.getmItemList().size(); i++) {
                        if (MenuList.getmItemList().get(i).getItemName().equals(itemName)) {
                            thisOrder.addMenuItemToOrder(MenuList.getmItemList().get(i), itemQuantity);
                            break;
                        }
                    }
                }

                InvoiceList.addInvoice(new Invoice(thisOrder, date));
            }
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
