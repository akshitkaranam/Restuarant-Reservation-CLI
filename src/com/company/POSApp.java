package com.company;

import com.company.administrative.Customer;
import com.company.administrative.MembershipList;
import com.company.administrative.Staff;
import com.company.administrative.StaffList;
import com.company.menuItem.MenuItem;
import com.company.menuItem.MenuList;
import com.company.menuItem.PromotionPackageMenu;
import com.company.restaurantfunctions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.List;

public class POSApp {

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
        Restaurant.addTable(9,10);
        Restaurant.addTable(10,10);

        //Membership List
        MembershipList.addMember(new Customer("Tom","12345678"));
        MembershipList.addMember(new Customer("Bob","23456789"));
        MembershipList.addMember(new Customer("David","34567891"));
        MembershipList.addMember(new Customer("Donald","45678901"));
        MembershipList.addMember(new Customer("Joe","56789012"));
        MembershipList.addMember(new Customer("Chris","67890123"));
        MembershipList.addMember(new Customer("Sally","78901234"));

        //StaffList
        StaffList.addUser("Christina","000001", Staff.JobRole.MANAGER);
        StaffList.addUser("Thomas","000002", Staff.JobRole.WAITER);
        StaffList.addUser("Lisa","000003", Staff.JobRole.WAITER);
        StaffList.addUser("Jessie","000004", Staff.JobRole.WAITER);
        StaffList.addUser("Fred","000005", Staff.JobRole.WAITER);
        StaffList.addUser("Wayne","000006", Staff.JobRole.WAITER);



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


        int option;
        Scanner scanner = new Scanner(System.in);
        option = 1;
        while (option != 12) {

            printMainMenu();
            option = scanner.nextInt();
            switch (option){

                case 1:
                    makeChangesToMenu();
                    break;
                case 2:
                    makeChangesToMenu();
                    break;
                case 3:
                    addReservation();
                    break;
                case 4:
                    showListOfReservationByDate();
                    break ;
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
                    changeStaff();
                case 12:
                    System.out.println("Terminating the system");
                    break;
                default:
                    System.out.println("Please choose from options 1-4");
                    break;
            }
        }
        scanner.close();
    }


    public static void printMainMenu(){
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
        System.out.println("11: Quit");
    }



    public static void makeChangesToMenu(){
        int option =1;
        Scanner sc = new Scanner(System.in);
        MenuList testMenu = new MenuList();
        PromotionPackageMenu testPromoMenu = new PromotionPackageMenu();

        do {
            System.out.println("MAKE CHANGES TO MENU");
            System.out.println("Choose an option:\n"
                    + "================================\n"
                    + "|1. Create a new menu item |\n"
                    + "|2. Update Menu Item|\n"
                    + "|3. Remove Menu Item |\n"
                    + "|4. Display Menu|\n"
                    + "|5. Quit from Menu Changes|\n"
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

    }

    public static void addReservation() {
        Scanner scanner = new Scanner(System.in);
        Set<LocalTime> availableSlotsSet = new LinkedHashSet<>();

        System.out.println("Enter the date you wish to reserve in the format YYYY-MM-DD");
        String dateEntered = scanner.next();
        LocalDate date = LocalDate.parse(dateEntered);


        System.out.println("Please enter your group size: ");
        int groupSize = scanner.nextInt();


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
            System.out.println("Sorry there are no tables available on this date: " + date + "for a group size of " + groupSize);
            return;
        }

        System.out.println("Please enter the time you want to book:  e.g 09:30 ");
        String timeEntered = scanner.next();
        timeEntered = timeEntered + ":00";
        LocalTime time = LocalTime.parse(timeEntered);
        System.out.println("Please enter the the duration you want to reserve the table in hours (e.g. 1.5 or 2");
        double duration = scanner.nextDouble();
        int durationInMinutes = (int) (60 * duration);
        boolean reservationIsPossible = false;


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
            if(isMemberInput.equalsIgnoreCase("y")){
                System.out.println("Please enter your contact number");
                contactNumber = scanner.next();

                if(MembershipList.getMembersList().containsKey(contactNumber)){
                    name = MembershipList.getMembersList().get(contactNumber).getName();
                    System.out.println("Confirm your name: "
                            + name + " y/n" );
                    String nameIsCorrect = scanner.next();
                    if(nameIsCorrect.equalsIgnoreCase("y")){
                        thisCustomer = MembershipList.getMembersList().get(contactNumber);
                    }else{
                        System.out.println("Sorry you are not a member!");
                        System.out.println("Please enter your name: ");
                        name = scanner.next();
                        thisCustomer = new Customer(name,contactNumber);
                    }

                }else{
                    System.out.println("Sorry you are not a member!");
                    System.out.println("Please enter your name: ");
                    name = scanner.next();
                    thisCustomer = new Customer(name,contactNumber);
                }

            }else{
                System.out.println("Please enter your name: ");
                name = scanner.next();
                System.out.println("Please enter your contact number");
                contactNumber = scanner.next();
                thisCustomer = new Customer(name,contactNumber);
            }

            Order thisOrder = new Order(thisCustomer,groupSize,tableEntry.getValue().getTableNumber()
                    ,date,time,time.plusMinutes(durationInMinutes));

//            tableEntry.getValue().addReservation(reservation);
            tableEntry.getValue().getTableDateSlotsList().get(date).reserveSlot(time, thisOrder, durationInMinutes);

            System.out.println("Reservation is confirmed for " + name + " on " + date + " at " + time + " to " + time.plusMinutes(durationInMinutes));
            return;

        }
        System.out.println("Sorry Reservation isn't possible for this date, time and duration");
    }

    public static void showListOfReservationByDate() {
        System.out.println("Enter the date you wish to get the list of reservations in the format YYYY-MM-DD");
        Scanner scanner = new Scanner(System.in);
        String dateEntered = scanner.next();
        LocalDate date = LocalDate.parse(dateEntered);

        Set<Order> setOfAllReservation = new HashSet<>();


        for (Table table : Restaurant.getTableList().values()) {
            List<Order> reservations = table.getOrderReservationsByDateForTable(date);
            setOfAllReservation.addAll(reservations);
        }

        List<Order> listOfAllOrderReservations = new ArrayList<>(setOfAllReservation);
        if(listOfAllOrderReservations.isEmpty()){
            System.out.println("There are currently no reservations on this date!");
        }

        for (Order order : listOfAllOrderReservations) {
            if(order == null){
                continue;
            }
            System.out.println(order);
        }
    }

    private static void removeReservation() {

        System.out.println("Enter the date you wish to remove reservations in the format YYYY-MM-DD");
        Scanner scanner = new Scanner(System.in);
        String dateEntered = scanner.next();
        LocalDate date = LocalDate.parse(dateEntered);

        Set<Order> setOfAllReservation = new HashSet<>();

        for (Table table : Restaurant.getTableList().values()) {
            List<Order> reservations = table.getOrderReservationsByDateForTable(date);
            setOfAllReservation.addAll(reservations);
        }

        List<Order> listOfAllOrderReservations = new ArrayList<>(setOfAllReservation);

        if(listOfAllOrderReservations.isEmpty()){
            System.out.println("There are no reservations available!");
            return;
        }
        for (int i =0;i<listOfAllOrderReservations.size();i++) {
            if(listOfAllOrderReservations.get(i) == null){
                continue;
            }
            System.out.println( i +": "+ listOfAllOrderReservations.get(i));
        }
        int optionChosen = scanner.nextInt();;
        Order orderToRemove = listOfAllOrderReservations.get(optionChosen);
        LocalTime reservationStartTime = orderToRemove.getReservationStartTime();
        LocalTime reservationEndTime = orderToRemove.getReservationEndTime();
        Table tableOfOrder = Restaurant.getTableList().get(orderToRemove.getTableNumber());
        TableDateSlots todaySlotsForThisTable = tableOfOrder.getTableDateSlotsList().get(LocalDate.now());

        LocalTime time =reservationStartTime;
        while(time.isBefore(reservationEndTime)){
            todaySlotsForThisTable.getSlots().put(time,null);
            time = time.plusMinutes(30);
        }

        System.out.println("Successfully removed reservation of" + orderToRemove.getCustomer().getName()
                + " from " + orderToRemove.getReservationStartTime() + " to " + orderToRemove.getReservationEndTime());
    }

    private static void checkTableAvailability() {
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
        for(int i =0;i<orderOfAllReservationForToday.size();i++){
            Order thisOrder = orderOfAllReservationForToday.get(i);

            if(thisOrder != null && !thisOrder.isOrderIsActive()){
                inactiveAvailable = true;
                LocalTime reservationStartTime = thisOrder.getReservationStartTime().minusMinutes(20);
                LocalTime reservationEndTime = thisOrder.getReservationEndTime().minusMinutes(30);

                if(timeNow.isAfter(reservationStartTime) && timeNow.isBefore(reservationEndTime)) {
                    System.out.println(i + ": " + thisOrder.getCustomer().getName() + " "
                            + thisOrder.getCustomer().getContactNumber());
                }
            }
        }

        if(!inactiveAvailable){
            System.out.println("There are no reservations to currently check-in at this time!");
            return;
        }

        int optionSelected = scanner.nextInt();
        Order relevantOrder = orderOfAllReservationForToday.get(optionSelected);
        relevantOrder.setOrderIsActive(true);
        Restaurant.addActiveOrder(relevantOrder);
        System.out.println("Successfully checked-in " + relevantOrder.getCustomer().getName() + " with group size of "
                + relevantOrder.getGroupSize() + "! Please escort them to table number: " + relevantOrder.getTableNumber() );
    }

    private static void addItemsToOrder() {
        List<Order> activeOrders = Restaurant.getActiveOrders();
        Scanner sc = new Scanner(System.in);
        int i =0;
        if(activeOrders.isEmpty()){
            System.out.println("There are currently no active orders!");
            return;
        }
        System.out.println("Please select from the following orders to modify: " );

        for(Order order:activeOrders){
            System.out.println(i++ + ": " + order.getCustomer().getName() + ". Table Number: " + order.getTableNumber());
        }
        int chosenOption = sc.nextInt();
        Order relevantOrder = activeOrders.get(chosenOption);
        int option = 1;
        do {
            System.out.println("MAKE CHANGES TO Order to table number: " + relevantOrder.getTableNumber());
            System.out.println("Choose an option:\n"
                    + "================================\n"
                    + "|1. Add MenuItem |\n"
                    + "|2. Modify Quantity\n"
                    + "|3. Remove MenuItem |\n"
                    + "|4. Show Added MenuItems and Quantity|\n"
                    + "|5. Quit Making Changes to Order\n"
                    + "==================================");
            option = sc.nextInt();
            switch (option) {
                case 1:
                    for(int j =0;j<MenuList.getmItemList().size();j++){
                        System.out.println(j+ " " + MenuList.getmItemList().get(j));
                    }
                    int innerChosenOption = sc.nextInt();
                    System.out.println("Enter Quantity: ");
                    int quantity = sc.nextInt();
                    relevantOrder.addMenuItemToOrder(MenuList.getmItemList().get(innerChosenOption),quantity);
                    break;
                case 2:
                    List<MenuItem> menuItemInOrder = new ArrayList<>(relevantOrder.getItemsOrderedList().keySet());
                    int j =0;
                    for(MenuItem menuItem : menuItemInOrder){
                        System.out.println(j++ +": "+ menuItem +", Quantity: "
                                + relevantOrder.getItemsOrderedList().get(menuItem));
                    }
                    innerChosenOption = sc.nextInt();
                    MenuItem chosenMenuItem = menuItemInOrder.get(innerChosenOption);
                    System.out.println("Please enter new quantity: ");
                    quantity = sc.nextInt();
                    relevantOrder.editQuantity(chosenMenuItem,quantity);
                    break;

                case 3:
                    menuItemInOrder = new ArrayList<>(relevantOrder.getItemsOrderedList().keySet());
                    j =0;
                    for(MenuItem menuItem : menuItemInOrder){
                        System.out.println(j++ +": "+ menuItem +", Quantity: "
                                + relevantOrder.getItemsOrderedList().get(menuItem));
                    }
                    innerChosenOption = sc.nextInt();
                    chosenMenuItem = menuItemInOrder.get(innerChosenOption);
                    relevantOrder.deleteMenuItem(chosenMenuItem);
                    break;
                case 4:
                    j =0;
                    menuItemInOrder = new ArrayList<>(relevantOrder.getItemsOrderedList().keySet());
                    for(MenuItem menuItem : menuItemInOrder){
                        System.out.println(j++ +": "+ menuItem +", Quantity: "
                                + relevantOrder.getItemsOrderedList().get(menuItem));
                    }
                    break;
                case 5:
                    System.out.println("Quitting updating the menu");
                    break;
                default:
                    System.out.println("Choose option (1-5):");
                    break;
            }

        } while (option !=5);
    }


    private static void checkOutCustomer() {

        List<Order> activeOrders = Restaurant.getActiveOrders();
        if(activeOrders.isEmpty()){
            System.out.println("There are currently no active orders!");
            return;
        }
        System.out.println("Choose the from the following reservation to check-out: ");
        Scanner scanner = new Scanner(System.in);
        int i =0;
        for(Order order:activeOrders){
            System.out.println(i++ + ": " + order.getCustomer().getName() + ". Table Number: " + order.getTableNumber());
        }
        int optionChosen = scanner.nextInt();
        Order orderToCheckOut = activeOrders.get(optionChosen);
        Invoice thisOrderInvoice = new Invoice(orderToCheckOut);
        thisOrderInvoice.generateReceipt();
        activeOrders.remove(optionChosen);

        //Remove Reservation
        LocalTime reservationStartTime = orderToCheckOut.getReservationStartTime();
        LocalTime reservationEndTime = orderToCheckOut.getReservationEndTime();
        Table tableOfOrder = Restaurant.getTableList().get(orderToCheckOut.getTableNumber());
        TableDateSlots todaySlotsForThisTable = tableOfOrder.getTableDateSlotsList().get(LocalDate.now());

        LocalTime time =reservationStartTime;
        while(time.isBefore(reservationEndTime)){
            todaySlotsForThisTable.getSlots().put(time,null);
            time = time.plusMinutes(30);
        }
    }

    private static void printSalesByTimePeriod() {
        int option =1;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("Choose an option:\n"
                    + "================================\n"
                    + "|1. Get Report By Day |\n"
                    + "|2. Get Report By Month|\n"
                    + "|3. Quit|\n"
                    + "==================================");
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

        } while (option !=3);
    }


    private static void changeStaff() {
        StaffList.getStaffList().values();
    }
}
