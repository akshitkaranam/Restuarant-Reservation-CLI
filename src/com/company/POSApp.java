package com.company;

import com.company.administrative.Customer;
import com.company.administrative.MembershipList;
import com.company.administrative.Staff;
import com.company.administrative.StaffList;
import com.company.menuItem.MenuItem;
import com.company.menuItem.MenuList;
import com.company.menuItem.PromotionPackage;
import com.company.menuItem.PromotionPackageMenu;
import com.company.restaurantessentials.*;

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

        //Retrieve Saved Information from CSV Files
        retrieveRestaurantInformation();
        retrieveStaffInformation();
        retrieveMembershipInformation();
        retrieveMenuInformation();
        retrievePromotionPackageInformation();
        retrieveOrderReservationInformation();
        retrieveActiveOrderInformation();
        retrieveInvoicesInformation();

        currentStaffUser = StaffList.getStaffList().get(0); //Default is index 0


        int option;
        Scanner scanner = new Scanner(System.in);
        option = 1;
        while (option != 12) {
            System.out.println("Current Staff User is: " + currentStaffUser.getName() + ", "
                    + currentStaffUser.getJobRole());

            printMainMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1 -> makeChangesToMenu();
                case 2 -> makeChangesToPackages();
                case 3 -> checkTableAvailability();
                case 4 -> addReservation();
                case 5 -> showListOfReservationByDate();
                case 6 -> removeReservation();
                case 7 -> checkInCustomer();
                case 8 -> modifyActiveOrder();
                case 9 -> checkOutCustomer();
                case 10 -> printSalesByTimePeriod();
                case 11 -> changeStaffUser();
                case 12 -> System.out.println("Terminating the system! :)");
                default -> System.out.println("Please choose from options 1-12");
            }
        }
        scanner.close();
    }


    public static void printMainMenu() {
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
                case 5 -> System.out.println("Exiting MenuApp");
                default -> System.out.println("Choose option (1-5):");
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

    private static void checkTableAvailability() {

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


        List<Order> orderOfAllReservationForToday = new ArrayList<>(setOfAllReservation);
        boolean inactiveAvailable = false;
        List<Integer> availableIndex = new ArrayList<>();
        for (int i = 0; i < orderOfAllReservationForToday.size(); i++) {
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
                        System.out.println((i + 1) + ": " + thisOrder.getCustomer().getName() + " "

                                + thisOrder.getCustomer().getContactNumber());
                        availableIndex.add(i + 1);

//                    }
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

            Order relevantOrder = orderOfAllReservationForToday.get(optionSelected - 1);
            relevantOrder.setOrderIsActive(true);
            Restaurant.addActiveOrder(relevantOrder);
            Restaurant.processActiveOrderToCSV();
            System.out.println("Successfully checked-in " + relevantOrder.getCustomer().getName() + " with group size of "
                    + relevantOrder.getGroupSize() + "! Please escort them to table number: " + relevantOrder.getTableNumber());
            System.out.println();
        }
    }

    private static void modifyActiveOrder() {
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
        int chosenOption = sc.nextInt();
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
                        innerChosenOption = sc.nextInt();
                        if (innerChosenOption <= j && innerChosenOption >= 0) {
                            break;
                        } else {
                            System.out.println("Please enter a valid choice!");
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
                        innerChosenOption = sc.nextInt();
                        if (innerChosenOption <= j && innerChosenOption >= 0) {
                            break;
                        } else {
                            System.out.println("Please enter a valid choice!");
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
                        innerChosenOption = sc.nextInt();
                        if (innerChosenOption <= j && innerChosenOption >= 0) {
                            break;
                        } else {
                            System.out.println("Please enter a valid choice!");
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
                        innerChosenOption = sc.nextInt();
                        if (innerChosenOption <= j && innerChosenOption >= 0) {
                            break;
                        } else {
                            System.out.println("Please enter a valid choice!");
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
                        innerChosenOption = sc.nextInt();
                        if (innerChosenOption <= j && innerChosenOption >= 0) {
                            break;
                        } else {
                            System.out.println("Please enter a valid choice!");
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
                        innerChosenOption = sc.nextInt();
                        if (innerChosenOption <= j && innerChosenOption >= 0) {
                            break;
                        } else {
                            System.out.println("Please enter a valid choice!");
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
        System.out.println(activeOrders);
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


    private static void changeStaffUser() {
        List<Staff> staffList = StaffList.getStaffList();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Current Staff User Logged-in: " + currentStaffUser.getName() + ": "
                + currentStaffUser.getJobRole().name());

        System.out.println("Please choose from the following: ");
        for (int i = 0; i < staffList.size(); i++) {
            System.out.println((i + 1) + ": " + staffList.get(i).getName() + ", " + staffList.get(i).getJobRole());
        }
        int chosenOption = scanner.nextInt();
        currentStaffUser = staffList.get(chosenOption - 1);
        System.out.println();
    }

    private static void retrieveRestaurantInformation() {

        try {
            // CSV file delimiter
            String DELIMITER = ",";

            // create a reader
            BufferedReader br = Files.newBufferedReader(Paths.get("src/com/company/Restaurant.csv"));

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

    private static void retrieveStaffInformation() {

        try {
            // CSV file delimiter
            String DELIMITER = ",";

            // create a reader
            BufferedReader br = Files.newBufferedReader(Paths.get("src/com/company/StaffList.csv"));

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

    private static void retrieveMembershipInformation() {

        try {
            // CSV file delimiter
            String DELIMITER = ",";

            // create a reader
            BufferedReader br = Files.newBufferedReader(Paths.get("src/com/company/MembersList.csv"));

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

    private static void retrievePromotionPackageInformation() {
        try {
            // CSV file delimiter
            String DELIMITER = ";";

            // create a reader
            BufferedReader br = Files.newBufferedReader(Paths.get("src/com/company/promotionPackage.csv"));

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
