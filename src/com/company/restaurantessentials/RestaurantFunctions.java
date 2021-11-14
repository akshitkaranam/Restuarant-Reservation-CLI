package com.company.restaurantessentials;

import com.company.administrative.Customer;
import com.company.administrative.MembershipList;
import com.company.administrative.Staff;
import com.company.administrative.StaffList;
import com.company.menu.MenuItem;
import com.company.menu.MenuList;
import com.company.menu.PromotionPackage;
import com.company.menu.PromotionPackageMenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * This is the class that contains all important restaurant functions.
 * There is only one attribute in this class: currentStaffUser, that tracks which staffUser is currently operating the
 * system.
 */

public class RestaurantFunctions {


    private static Staff currentStaffUser;

    /**
     * Sets the staff who is using the system
     * @param currentStaff Staff object that is using this system
     */
    public static void setCurrentStaff(Staff currentStaff) {
        RestaurantFunctions.currentStaffUser = currentStaff;
    }


    /**
     * Returns the current staff user who is using the system
     * @return current staff user
     */
    public static Staff getCurrentStaffUser() {
        return currentStaffUser;
    }


    /**
     * This function creates/updates/display's the menu. It gives full access to control the items in the menu.
     */
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
                case 1 -> {
                    testMenu.createMenuItem();
                    System.out.println("A new item has been created");
                }
                case 2 -> testMenu.updateMenuItem();
                case 3 -> testMenu.removeMenuItem();
                case 4 -> testMenu.displayMenu();
                case 5 -> System.out.println("Exiting MenuApp");
                case 6 -> testPromoMenu.addPromotionPackage();
                case 7 -> testPromoMenu.removePromotionPackage();
                case 8 -> testPromoMenu.displayPackageMenu();
                case 9 -> testPromoMenu.updatePromotionPackage();
                default -> System.out.println("Choose option (1-5):");
            }

        } while (option != 5);

    }


    /**
     * This function creates/updates/display's the PromotionPackages.
     * It gives full access to control the items in the PromotionPackages.
     */

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
                    |5. Quit from Promotion Package Changes|
                    ==================================""");
            option = sc.nextInt();
            switch (option) {
                case 1 -> testPromoMenu.addPromotionPackage();
                case 2 -> testPromoMenu.updatePromotionPackage();
                case 3 -> testPromoMenu.removePromotionPackage();
                case 4 -> testPromoMenu.displayPackageMenu();
                case 5 -> System.out.println("Exiting PromotionPackage Menu1");
                default -> System.out.println("Choose option (1-5):");
            }

        } while (option != 5);

    }

    /**
     * This function adds a reservation based on the user inputs.
     * <ol>
     *     <li> Reservation can only be added for the next 14 days
     *     <li> Reservation cannot be made in the past
     *     <li> Reservation cannot be made if there are no empty tables at the specified date/time/groupSize
     *     <li> Slots can only be reserved in slots of 30 minutes
     *     <li> Reservations cannot be made beyond the restaurant's opening hours
     *     <li> Member authentication is also done here
     * </ol>
     */
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

        if (availableSlotsSet.size() == 0) {
            System.out.println("Sorry there are no tables available on this date: " + date + " for a group size of " + groupSize);
            System.out.println();
            return;
        }

        while (true) {
            try {
                System.out.println("Please enter the time you want to book in the format hh:mm (e.g. 09:00) : ");
                String timeEntered = scanner.next();
                timeEntered = timeEntered + ":00";
                time = LocalTime.parse(timeEntered);

                if(time.isBefore(Restaurant.getOpeningTime()) || time.isAfter(Restaurant.getClosingTime())){
                    System.out.println("You cannot reserve slots beyond the restaurant's operating hours");
                    continue;
                }

                if (time.getMinute() != 0 && time.getMinute() != 30) {
                    System.out.println("You can only reserve slots in increments of 30 minutes. Please try again!");
                    continue;
                }

                if (LocalDateTime.of(date, time).isBefore(LocalDateTime.now().minusMinutes(10))) {
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
                System.out.println("Please enter the the duration you want to reserve the table in hours (e.g. 1.5/2)");
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

            System.out.println("Reservation is confirmed for " + name + " on " + date + " at " + time + " to " + time.plusMinutes(durationInMinutes)
                    + ". Reserved Table Number is: " + thisOrder.getTableNumber());
            System.out.println();
            Restaurant.processActiveReservationsToCSV();
            return;

        }
        System.out.println("Sorry Reservation isn't possible for this date, time and duration");
        System.out.println();
    }

    /**
     * This function displays the list of reservation when the user enters the relevant date.
     *
     * <ol>
     *     <li> Can only access reervations for the next 14 days.
     *     <li> Past Reservations cannot be accessed, since they are either deleted when the user checks-out or when
     *          the customer doesn't check in within 30 minutes
     *     <li> In this function, if the customer doesn't reach within 30 minutes of the reservation start time, the
     *          reservation is automatically deleted after 30 minutes.
     * </ol>
     */

    public static void showListOfActiveReservationByDate() {
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
        if (listOfAllOrderReservations.size() == 0) {
            System.out.println("There are currently no reservations on this date!");
            System.out.println();
            return;
        }


        for (Order order : listOfAllOrderReservations) {
            if (order == null) {
                continue;
            }

            //remove if reservation has expired!
            LocalDate reservationDate = order.getDate();
            if (reservationDate.equals(LocalDate.now()) &&
                    order.getReservationStartTime().plusMinutes(30).isBefore(LocalTime.now())
                    && !order.isOrderIsActive()) {
                LocalTime reservationStartTime = order.getReservationStartTime();
                LocalTime reservationEndTime = order.getReservationEndTime();
                Table tableOfOrder = Restaurant.getTableList().get(order.getTableNumber());


                TableDateSlots todaySlotsForThisTable = tableOfOrder.getTableDateSlotsList().get(LocalDate.now());
                LocalTime time = reservationStartTime;
                while (time.isBefore(reservationEndTime)) {
                    todaySlotsForThisTable.getSlots().put(time, null);
                    time = time.plusMinutes(30);
                }
                Restaurant.processActiveReservationsToCSV();
                continue;
            }

            System.out.println(order);
        }
        System.out.println();
    }


    /**
     * This function deletes the reservation given the date.
     * <ol>
     *     <li>Reservations with active order cannot be removed
     *     <li>Reservation can be only removed for the next 14 days
     * </ol>
     */
    public static void removeReservation() {


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
                    System.out.println("You can only remove reservations for the next 14 days!");
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
        if (orderToRemove.isOrderIsActive()) {
            System.out.println("Reservation cannot be removed when Order it is active!");
            return;
        }

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
        System.out.println("Successfully removed reservation of " + orderToRemove.getCustomer().getName()
                + " from " + orderToRemove.getReservationStartTime() + " to " + orderToRemove.getReservationEndTime());
        System.out.println();
    }


    /**
     * This checks the availability of the tables given the date and time. It gives a result of the tables that are free
     * for the next 1.5h starting from the time given.
     */
    public static void checkTableAvailability() {

        LocalDate date;
        LocalTime time;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Enter the date you wish to check the table availability");
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
                if(time.isBefore(Restaurant.getOpeningTime()) || time.isAfter(Restaurant.getClosingTime())){
                    System.out.println("You cannot check for slots beyond the restaurant's operating hours");
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
        //code changed here
        System.out.println("These are the empty tables at this time, for a duration of atleast 1.5 hours");
        for (Table table : Restaurant.getTableList().values()) {
            if (!table.getTableDateSlotsList().containsKey(date)) {
                table.getTableDateSlotsList().put(date, new TableDateSlots(date));
            }
            //code changed here
            if (table.getTableDateSlotsList().get(date).getSlots().get(time) == null &&
                    table.getTableDateSlotsList().get(date).getSlots().get(time.plusMinutes(30)) == null
                    && table.getTableDateSlotsList().get(date).getSlots().get(time.plusMinutes(60)) == null) {
                System.out.println("Table Number: " + table.getTableNumber() + ", Number of Seats: " + table.getNumberOfSeats());
                allFull = false;
            }
        }
        System.out.println();

        if (allFull) {
            System.out.println("All the tables are full at this time!");
        }
        System.out.println();

    }


    /**
     * This function checks-in the customer
     * <ol>
     *     <li>Customers can only check in 15 minutes before the reservation start time (and date as well).
     *     <li>In this function, if the customer doesn't reach within 30 minutes of the reservation start time, the
     *         reservation is automatically deleted after 30 minutes.
     *     <li>Once the customer is checked in, his orderReservation becomes 'active' and is added to the activeOrderList
     *         in the restaurant class.
     *     <li> Once the check-in is done, the activeOrder.csv is ammended to reflect the changes
     * </ol>
     */
    public static void checkInCustomer() {
        LocalDate dateNow = LocalDate.now();
        LocalTime timeNow = LocalTime.now();
        Collection<Table> tableList = Restaurant.getTableList().values();
        Set<Order> setOfAllReservation = new HashSet<>();
        Scanner scanner = new Scanner(System.in);

        for (Table table : tableList) {
            List<Order> reservations = table.getOrderReservationsByDateForTable(dateNow);
            setOfAllReservation.addAll(reservations);
        }


        List<Order> orderOfAllReservationForToday = new ArrayList<>(setOfAllReservation);
        boolean inactiveAvailable = false;
        List<Integer> availableIndex = new ArrayList<>();
        outer: for (int i = 0; i < orderOfAllReservationForToday.size(); i++) {
            Order thisOrder = orderOfAllReservationForToday.get(i);

            if (thisOrder != null) {
                //Remove Reservation if time now is 30 minutes after the reservation start time
                LocalDate reservationDate = thisOrder.getDate();
                if (reservationDate.equals(LocalDate.now()) &&
                        thisOrder.getReservationStartTime().plusMinutes(30).isBefore(LocalTime.now())
                        && !thisOrder.isOrderIsActive()) {


                    LocalTime reservationStartTime = thisOrder.getReservationStartTime();
                    LocalTime reservationEndTime = thisOrder.getReservationEndTime();
                    Table tableOfOrder = Restaurant.getTableList().get(thisOrder.getTableNumber());
                    TableDateSlots todaySlotsForThisTable = tableOfOrder.getTableDateSlotsList().get(LocalDate.now());
                    LocalTime time = reservationStartTime;
                    while (time.isBefore(reservationEndTime)) {
                        todaySlotsForThisTable.getSlots().put(time, null);
                        time = time.plusMinutes(30);
                    }
                    Restaurant.processActiveReservationsToCSV();
                    continue;
                }

                if (!thisOrder.isOrderIsActive()) {
                    inactiveAvailable = true;
                    LocalTime reservationStartTime = thisOrder.getReservationStartTime().minusMinutes(20);
                    LocalTime reservationEndTime = thisOrder.getReservationEndTime().minusMinutes(30);

                    if (timeNow.isAfter(reservationStartTime) && timeNow.isBefore(reservationEndTime)) {
                        System.out.println((i) + ": " + thisOrder.getCustomer().getName() + " "

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
        System.out.println("Choose the from the following reservation to check-in: ");
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

        for(Order activeOrder : Restaurant.getActiveOrders()){
            if(relevantOrder.getTableNumber() == activeOrder.getTableNumber()){
                System.out.println("Sorry, check-in is not possible as previous customer: " +
                        activeOrder.getCustomer().getName() + " is still dining at table number: "
                        +activeOrder.getTableNumber());
                return;
            }
        }


        relevantOrder.setOrderIsActive(true);
        Restaurant.addActiveOrder(relevantOrder);
        Restaurant.processActiveOrderToCSV();
        System.out.println("Successfully checked-in " + relevantOrder.getCustomer().getName() + " with group size of "
                + relevantOrder.getGroupSize() + "! Please escort them to table number: " + relevantOrder.getTableNumber());
        System.out.println();

    }

    /**
     * This accesses the activeOrderList in the Restaurant class. The active orders can be amended based on the user input:
     * <ol>
     *     <li>Add MenuItem
     *     <li>Add Remove MenuItem
     *     <li>Add Change quantity of MenuItem
     *     <li>Add PromotionPackage
     *     <li>Add Remove PromotionPackage
     *     <li>Add Change quantity of PromotionPackage
     * </ol>
     * Any changes to any of the above would amend the activeOrder.csv file
     */
    public static void modifyActiveOrder() {
        List<Order> activeOrders = Restaurant.getActiveOrders();
        Scanner sc = new Scanner(System.in);
        int i = 0;
        if (activeOrders.isEmpty()) {
            System.out.println("There are currently no active orders!");
            return;
        }
        System.out.println("Please select from the following orders to modify: ");

        for (Order order : activeOrders) {
            System.out.println(++i + ": " + order.getCustomer().getName() + ". Table Number: " + order.getTableNumber());
        }
        int chosenOption;
        while (true) {
            try {
                chosenOption = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                e.printStackTrace();
            }
        }

        Order relevantOrder = activeOrders.get(chosenOption - 1);
        relevantOrder.setStaff(currentStaffUser);
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
                    |9. Quit Making Changes to Order
                    ==================================""");
            option = sc.nextInt();
            switch (option) {
                case 1:
                    int innerChosenOption;
                    int j;
                    for (j = 0; j < MenuList.getmItemList().size(); j++) {
                        System.out.println((j + 1) + " " + MenuList.getmItemList().get(j));
                    }

                    while (true) {

                        try {
                            innerChosenOption = sc.nextInt();
                            if (innerChosenOption <= j && innerChosenOption >= 0) {
                                break;
                            } else {
                                System.out.println("Please enter a valid choice!");
                            }
                        } catch (InputMismatchException e) {
                            e.printStackTrace();
                        }

                    }

                    System.out.println("Enter Quantity: ");
                    int quantity = sc.nextInt();
                    relevantOrder.addMenuItemToOrder(MenuList.getmItemList().get(innerChosenOption - 1), quantity);
                    System.out.println("Successfully added: " + MenuList.getmItemList().get(innerChosenOption - 1)
                            .getItemName() + ", Quantity: " + quantity);
                    break;

                case 2:
                    List<MenuItem> menuItemInOrder = new ArrayList<>(relevantOrder.getItemsOrderedList().keySet());
                    j = 0;
                    for (MenuItem menuItem : menuItemInOrder) {
                        System.out.println(++j + ": " + menuItem + ", Quantity: "
                                + relevantOrder.getItemsOrderedList().get(menuItem));
                    }

                    while (true) {

                        try {
                            innerChosenOption = sc.nextInt();
                            if (innerChosenOption <= j && innerChosenOption >= 0) {
                                break;
                            } else {
                                System.out.println("Please enter a valid choice!");
                            }
                        } catch (InputMismatchException e) {
                            e.printStackTrace();
                        }

                    }

                    MenuItem chosenMenuItem = menuItemInOrder.get(innerChosenOption - 1);
                    System.out.println("Please enter new quantity: ");
                    quantity = sc.nextInt();
                    relevantOrder.editQuantity(chosenMenuItem, quantity);

                    relevantOrder.addMenuItemToOrder(MenuList.getmItemList().get(innerChosenOption - 1), quantity);
                    System.out.println("Successfully changed quantity of: "
                            + chosenMenuItem.getItemName() +
                            ", Quantity: " + quantity);
                    break;

                case 3:
                    menuItemInOrder = new ArrayList<>(relevantOrder.getItemsOrderedList().keySet());
                    j = 0;
                    for (MenuItem menuItem : menuItemInOrder) {
                        System.out.println(++j + ": " + menuItem + ", Quantity: "
                                + relevantOrder.getItemsOrderedList().get(menuItem));
                    }
                    while (true) {

                        try {
                            innerChosenOption = sc.nextInt();
                            if (innerChosenOption <= j && innerChosenOption >= 0) {
                                break;
                            } else {
                                System.out.println("Please enter a valid choice!");
                            }
                        } catch (InputMismatchException e) {
                            e.printStackTrace();
                        }

                    }
                    chosenMenuItem = menuItemInOrder.get(innerChosenOption - 1);
                    relevantOrder.deleteMenuItem(chosenMenuItem);
                    System.out.println("Successfully deleted: "
                            + chosenMenuItem.getItemName() + " from this Order! ");
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
                    for (j = 0; j < PromotionPackageMenu.getPackageList().size(); j++) {
                        System.out.println((j + 1) + " " + PromotionPackageMenu.getPackageList().get(j));
                    }

                    while (true) {
                        try {
                            innerChosenOption = sc.nextInt();
                            if (innerChosenOption <= j && innerChosenOption >= 0) {
                                break;
                            } else {
                                System.out.println("Please enter a valid choice!");
                            }
                        } catch (InputMismatchException e) {
                            e.printStackTrace();
                        }

                    }

                    System.out.println("Enter Quantity: ");
                    quantity = sc.nextInt();
                    relevantOrder.addPromotionPackageToOrder(PromotionPackageMenu.
                            getPackageList().get(innerChosenOption - 1), quantity);

                    System.out.println("Successfully added: " + PromotionPackageMenu.
                            getPackageList().get(innerChosenOption - 1)
                            .getPackageName() + ", Quantity: " + quantity);
                    break;


                case 6:
                    List<PromotionPackage> promotionPackageList
                            = new ArrayList<>(relevantOrder.getPromotionPackageOrderedList().keySet());
                    j = 0;
                    for (PromotionPackage promoPack : promotionPackageList) {
                        System.out.println(++j + ": " + promoPack + ", Quantity: "
                                + relevantOrder.getPromotionPackageOrderedList().get(promoPack));
                    }

                    while (true) {
                        try {
                            innerChosenOption = sc.nextInt();
                            if (innerChosenOption <= j && innerChosenOption >= 0) {
                                break;
                            } else {
                                System.out.println("Please enter a valid choice!");
                            }
                        } catch (InputMismatchException e) {
                            e.printStackTrace();
                        }

                    }

                    PromotionPackage chosenPromoPack = promotionPackageList.get(innerChosenOption - 1);
                    System.out.println("Please enter new quantity: ");
                    quantity = sc.nextInt();
                    relevantOrder.editQuantity(chosenPromoPack, quantity);

                    System.out.println("Successfully changed quantity of: "
                            + chosenPromoPack.getPackageName() +
                            ", Quantity: " + quantity);
                    break;

                case 7:
                    promotionPackageList
                            = new ArrayList<>(relevantOrder.getPromotionPackageOrderedList().keySet());
                    j = 0;
                    for (PromotionPackage promoPack : promotionPackageList) {
                        System.out.println(++j + ": " + promoPack + ", Quantity: "
                                + relevantOrder.getPromotionPackageOrderedList().get(promoPack));
                    }
                    while (true) {
                        try {
                            innerChosenOption = sc.nextInt();
                            if (innerChosenOption <= j && innerChosenOption >= 0) {
                                break;
                            } else {
                                System.out.println("Please enter a valid choice!");
                            }
                        } catch (InputMismatchException e) {
                            e.printStackTrace();
                        }

                    }

                    chosenPromoPack = promotionPackageList.get(innerChosenOption - 1);
                    relevantOrder.deletePromotionPackage(chosenPromoPack);
                    System.out.println("Successfully deleted: "
                            + chosenPromoPack.getPackageName() + " from this Order! ");
                    break;

                case 8:
                    j = 0;
                    promotionPackageList
                            = new ArrayList<>(relevantOrder.getPromotionPackageOrderedList().keySet());
                    for (PromotionPackage promoPack : promotionPackageList) {
                        System.out.println(j++ + ": " + promoPack + ", Quantity: "
                                + relevantOrder.getItemsOrderedList().get(promoPack));
                    }
                    break;

                case 9:
                    System.out.println("Quitting updating active orders");
                    break;

                default:
                    System.out.println("Choose option (1-9):");
                    break;
            }

        } while (option != 9);

        Restaurant.processActiveOrderToCSV();
        System.out.println();
    }


    /**
     * This function checks out the customer.
     * <ol>
     *     <li>The invoice is then added into the InvoiceList.
     *     <li>The information from this invoice object is then written to the orderInvoice.csv file
     *    <li>Once the customer is checked-out, the final invoice is generated, and is assumed that he has made the payment
     * </ol>
     */
    public static void checkOutCustomer() {

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
            System.out.println((++i) + ": " + order.getCustomer().getName() + ". Table Number: " + order.getTableNumber());
        }

        while (true) {
            optionChosen = scanner.nextInt();
            if (optionChosen <= i && optionChosen >= 0) {
                break;
            } else {
                System.out.println("Please enter a valid choice!");
            }
        }

        Order orderToCheckOut = activeOrders.get(optionChosen - 1);
        orderToCheckOut.setStaff(currentStaffUser);
        Invoice thisOrderInvoice = new Invoice(orderToCheckOut);
        thisOrderInvoice.generateReceipt();
        activeOrders.remove(optionChosen - 1);
        Restaurant.processActiveOrderToCSV();

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

    /**
     * This function access the InvoiceList and generates revenue report based on the user input
     * <ol>
     *     <li>generates report by revenue
     *     <li>generates report by quantity sold
     *     <li>generated report on 2 different time periods - by day and by month
     * </ol>
     */
    public static void printSalesByTimePeriod() {
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
                case 1 -> {
                    System.out.println("Enter the desired date in the format YYYY-MM-DD");
                    String dateEntered = sc.next();
                    LocalDate date = LocalDate.parse(dateEntered);
                    InvoiceList.salesReportByDay(date);
                }
                case 2 -> {
                    System.out.println("Enter the date month in the format MM (i.e 12) ");
                    int monthEntered = sc.nextInt();
                    Month month = Month.of(monthEntered);
                    InvoiceList.salesReportByMonth(month);
                }
                case 3 -> System.out.println("Quitting, going back to Main Menu!");
                default -> System.out.println("Choose option (1-3):");
            }

        } while (option != 3);
        System.out.println();
    }


    /**
     * This function allows the change of StaffUser that is currently controlling the system.
     * The staff name is prominently used in the invoice and order objects.
     */
    public static void changeStaffUser() {
        List<Staff> staffList = StaffList.getStaffList();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Current Staff User Logged-in: " + currentStaffUser.getName() + ": "
                + currentStaffUser.getJobRole().name());

        System.out.println("Please choose from the following: ");
        for (int i = 0; i < staffList.size(); i++) {
            System.out.println((i + 1) + ": " + staffList.get(i).getName() + ", " + staffList.get(i).getJobRole());
        }
        int chosenOption;

        while (true) {
            try {
                chosenOption = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                e.printStackTrace();

            }
        }


        currentStaffUser = staffList.get(chosenOption - 1);
        System.out.println();
    }

    /**
     * This retrieves the Restaurant information such as:
     * <ol>
     *     <li>restaurant name
     *     <li>restaurant address
     *     <li>restaurant opening time
     *     <li>restaurant closing time
     *     <li>initialise the tables based on the Table Number and its capacity.
     * </ol>
     */

    public static void retrieveRestaurantInformation() {

        try {
            // CSV file delimiter
            String DELIMITER = ",";

            // create a reader
            BufferedReader br = Files.newBufferedReader(Paths.get("src/com/company/storeddata/Restaurant.csv"));

            // read the file line by line
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {

                if (i == 0) {
                    Restaurant.setName(line);
                    i++;
                    continue;
                } else if (i == 1) {
                    Restaurant.setAddress(line);
                    i++;
                    continue;
                }else if (i ==2){
                    Restaurant.setOpeningTime(LocalTime.parse(line));
                    i++;
                    continue;
                }else if (i==3){
                    Restaurant.setClosingTime(LocalTime.parse(line));
                    i++;
                    continue;
                }

                // convert line into tokens
                String[] tokens = line.split(DELIMITER);
                String tableNumber = tokens[0];
                String tableCapacity = tokens[1];
                Restaurant.addTable(Integer.parseInt(tableNumber), Integer.parseInt(tableCapacity));
                i++;
            }
            br.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This function reads the StaffList.csv information and creates appropriate Staff objects and adds them to
     * the StaffList class.
     */
    public static void retrieveStaffInformation() {

        try {
            // CSV file delimiter
            String DELIMITER = ",";

            // create a reader
            BufferedReader br = Files.newBufferedReader(Paths.get("src/com/company/storeddata/StaffList.csv"));

            // read the file line by line
            String line;
            while ((line = br.readLine()) != null) {
                // convert line into tokens
                String[] tokens = line.split(DELIMITER);
                String employeeName = tokens[0];
                String employeeID = tokens[1];
                String jobType = tokens[2];
                String gender = tokens[3];
                if (jobType.equalsIgnoreCase(Staff.JobRole.WAITER.name())) {
                    StaffList.addStaff(employeeName, employeeID, Staff.JobRole.WAITER, gender);
                } else {
                    StaffList.addStaff(employeeName, employeeID, Staff.JobRole.MANAGER, gender);
                }


            }
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This function reads the MembershipList.csv information and creates appropriate Customer objects and adds them to
     * the MembershipList class.
     */
    public static void retrieveMembershipInformation() {

        try {
            // CSV file delimiter
            String DELIMITER = ",";

            // create a reader
            BufferedReader br = Files.newBufferedReader(Paths.get("src/com/company/storeddata/MembersList.csv"));

            // read the file line by line
            String line;
            while ((line = br.readLine()) != null) {
                // convert line into tokens
                String[] tokens = line.split(DELIMITER);
                String memberName = tokens[0];
                String memberContactNumber = tokens[1];

                MembershipList.addMember(new Customer(memberName, memberContactNumber));


            }
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * This function reads the menu.csv information and creates appropriate MenuItem objects and adds them to
     * the MenuList
     */
    public static void retrieveMenuInformation() {

        try {
            // CSV file delimiter
            String DELIMITER = ";";

            // create a reader
            BufferedReader br = Files.newBufferedReader(Paths.get("src/com/company/storeddata/menu.csv"));

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


    /**
     * This function reads the promotionPackage.csv information and creates appropriate PromotionPackage objects and adds them to
     * the PromotionPackageMenu
     */
    public static void retrievePromotionPackageInformation() {
        try {
            // CSV file delimiter
            String DELIMITER = ";";

            // create a reader
            BufferedReader br = Files.newBufferedReader(Paths.get("src/com/company/storeddata/promotionPackage.csv"));

            // read the file line by line
            String line;
            while ((line = br.readLine()) != null) {
                // convert line into tokens
                String[] tokens = line.split(DELIMITER);
                String packageItemName = tokens[0];
                String packageDescription = tokens[1];
                String packagePrice = tokens[2];
                List<MenuItem> menuItemList = new ArrayList<>();

                for (int i = 3; i < tokens.length; i++) {
                    menuItemList.add(MenuList.getMenuItemFromList(tokens[i]));
                }
                PromotionPackage tempPromoPack = new PromotionPackage(menuItemList,
                        Double.parseDouble(packagePrice), packageDescription, packageItemName);
                PromotionPackageMenu.getPackageList().add(tempPromoPack);
            }
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * This function reads the orderReservation.csv information and creates appropriate
     * Order objects and adds them the appropriate TableDateSlots.
     */

    public static void retrieveOrderReservationInformation() {

        try {
            // CSV file delimiter
            String DELIMITER = ";";

            // create a reader
            BufferedReader br = Files.newBufferedReader(Paths.get("src/com/company/storeddata/orderReservations.csv"));

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
                String orderNumber = tokens[7];
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
                        , LocalDate.parse(date), LocalTime.parse(reservationStartTime)
                        , LocalTime.parse(reservationEndTime), Integer.parseInt(orderNumber));
                int duration = (int) Duration.between(LocalTime.parse(reservationStartTime), LocalTime.parse(reservationEndTime)).toMinutes();
                requiredDateSlots.reserveSlot(LocalTime.parse(reservationStartTime), thisOrder, duration);
            }

            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This function reads the activeOrder.csv information and creates appropriate
     * Order objects and adds them the appropriate ActiveOrders list in the Restauarnt class.
     */

    public static void retrieveActiveOrderInformation() {

        try {
            // CSV file delimiter
            String DELIMITER = ";";

            // create a reader
            BufferedReader br = Files.newBufferedReader(Paths.get("src/com/company/storeddata/activeOrders.csv"));

            // read the file line by line
            String line;
            while ((line = br.readLine()) != null) {
                // convert line into tokens
                String[] tokens = line.split(DELIMITER);
                String orderID = tokens[0];
                String listOfMenuItems = tokens[1];
                String listOfPromoItems = tokens[2];
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
                if (listOfMenuItems.length() != 0) {
                    String[] tokens2 = listOfMenuItems.split(", ");
//                System.out.println(Arrays.toString(tokens2));

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
                }


                listOfPromoItems = listOfPromoItems.substring(1, listOfPromoItems.length() - 1);
                if (listOfPromoItems.length() != 0) {
                    String[] tokens3 = listOfPromoItems.split(", ");
//                System.out.println(Arrays.toString(tokens3));

                    for (String token3 : tokens3) {
                        String[] tokens4 = token3.split("=");
                        name = tokens4[0];
                        quantity = Integer.parseInt(tokens4[1]);

                        for (int i = 0; i < PromotionPackageMenu.getPackageList().size(); i++) {
                            if (PromotionPackageMenu.getPackageList().get(i).getPackageName().equals(name)) {
                                thisOrder.addPromotionPackageToOrder(PromotionPackageMenu.getPackageList().get(i), quantity);
                            }
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

    /**
     * This function reads the orderInvoices.csv information and creates appropriate
     * Invoice objects and adds them the InvoiceList class.
     */


    public static void retrieveInvoicesInformation() {

        try {
            // CSV file delimiter
            String DELIMITER = ";";

            // create a reader
            BufferedReader br = Files.newBufferedReader(Paths.get("src/com/company/storeddata/orderInvoices.csv"));

            // read the file line by line
            String line;
            while ((line = br.readLine()) != null) {

                String itemName;
                int itemQuantity;
                String packageName;
                int packageQuantity;
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
                String listOfPromoItems = tokens[9];
                int tableNumber = Integer.parseInt(tokens[10]);
                String staffNameString = tokens[11];
                Staff staffName = null;
                for (Staff staff : StaffList.getStaffList()) {
                    if (staff.getName().equals(staffNameString)) {
                        staffName = staff;
                    }
                }


                if (MembershipList.getMembersList().containsKey(customerContactNumber) &&
                        MembershipList.getMembersList().get(customerContactNumber).getName().equals(customerName)) {
                    thisCustomer = MembershipList.getMembersList().get(customerContactNumber);
                } else {
                    thisCustomer = new Customer(customerName, customerContactNumber);
                }

                Order thisOrder = new Order(thisCustomer, orderNumber, groupSize, tableNumber, orderDate
                        , reservationStartTime, reservationEndTime, false, staffName, false);


                listOfMenuItems = listOfMenuItems.substring(1, listOfMenuItems.length() - 1);
                if (listOfMenuItems.length() != 0) {
                    String[] tokens2 = listOfMenuItems.split(", ");
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
                }


                listOfPromoItems = listOfPromoItems.substring(1, listOfPromoItems.length() - 1);

                if (listOfPromoItems.length() != 0) {
                    String[] tokens3 = listOfPromoItems.split(", ");
                    for (String token3 : tokens3) {
                        String[] tokens4 = token3.split("=");
                        packageName = tokens4[0];
                        packageQuantity = Integer.parseInt(tokens4[1]);

                        for (int i = 0; i < PromotionPackageMenu.getPackageList().size(); i++) {
                            if (PromotionPackageMenu.getPackageList().get(i).getPackageName().equals(packageName)) {
                                thisOrder.addPromotionPackageToOrder(PromotionPackageMenu.getPackageList().get(i), packageQuantity);
                            }
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