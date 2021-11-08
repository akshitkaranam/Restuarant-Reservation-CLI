package com.company.restaurantfunctions;

import com.company.menuItem.MenuItem;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.List;

public class InvoiceList {

    public static List<Invoice> invoicesList = new ArrayList<>();

    public static void addInvoice(Invoice invoice) {
        if (invoice.isInvoicePaid()) {
            invoicesList.add(invoice);
        } else {
            System.out.println("Invoice hasn't been paid yet!");
        }
    }

    public static void salesReportByDay(LocalDate date) {
        List<Invoice> relevantInvoices = new ArrayList<>(invoicesList);
        relevantInvoices.removeIf(n -> !n.getDate().equals(date));
        int option = 1;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("Choose an option:\n"
                    + "================================\n"
                    + "|1. Get Total Revenue |\n"
                    + "|2. Get Sales of Individual Items|\n"
                    + "|3. Quit|\n"
                    + "==================================");
            option = sc.nextInt();
            switch (option) {
                case 1:
                    int totalRevenue = calculateTotalRevenue(relevantInvoices);
                    System.out.println("Total Revenue for Today is: " + totalRevenue);
                    break;
                case 2:
                    Map<MenuItem, Integer> totalItemQuantity = getMenuItemQuantity(relevantInvoices);

                    for (Map.Entry<MenuItem, Integer> menuItemQuantityEntry : totalItemQuantity.entrySet()) {
                        System.out.println(menuItemQuantityEntry.getKey() + ": " + menuItemQuantityEntry.getValue());
                    }
                    break;
                case 3:
                    System.out.println("Quitting, going back to Main Menu!");
                    break;

                default:
                    System.out.println("Choose option (1-3):");
                    break;
            }

        } while (option != 3);
    }


    public static void salesReportByMonth(Month month) {
        List<Invoice> relevantInvoices = new ArrayList<>(invoicesList);
        relevantInvoices.removeIf(n -> !n.getMonth().equals(month));
        int option = 1;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("Choose an option:\n"
                    + "================================\n"
                    + "|1. Get Total Revenue |\n"
                    + "|2. Get Sales of Individual Items|\n"
                    + "|3. Quit|\n"
                    + "==================================");
            option = sc.nextInt();
            switch (option) {
                case 1:
                    int totalRevenue = calculateTotalRevenue(relevantInvoices);
                    System.out.println("Total Revenue for Month is: " + totalRevenue);
                    break;
                case 2:
                    Map<MenuItem, Integer> totalItemQuantity = getMenuItemQuantity(relevantInvoices);

                    System.out.println("Total Item Sales for Month is: ");
                    for (Map.Entry<MenuItem, Integer> menuItemQuantityEntry : totalItemQuantity.entrySet()) {
                        System.out.println(menuItemQuantityEntry.getKey() + ": " + menuItemQuantityEntry.getValue());
                    }
                    break;
                case 3:
                    System.out.println("Quitting, going back to Main Menu!");
                    break;

                default:
                    System.out.println("Choose option (1-3):");
                    break;
            }

        } while (option != 3);
    }


    private static int calculateTotalRevenue(List<Invoice> relevantInvoices) {
        int totalRevenue = 0;
        for (Invoice in : relevantInvoices) {
            totalRevenue += in.getPriceAfterTax();
        }

        return totalRevenue;
    }

    private static Map<MenuItem, Integer> getMenuItemQuantity(List<Invoice> relevantInvoices) {
        Map<MenuItem, Integer> totalItemQuantity = new HashMap<>();

        for (Invoice in : relevantInvoices) {
            Map<MenuItem, Integer> rawMap = in.getOrder().getItemsOrderedList();
            for (Map.Entry<MenuItem, Integer> menuItemQuantityEntry : rawMap.entrySet()) {

                MenuItem tempItem = menuItemQuantityEntry.getKey();
                int addedQuantity = menuItemQuantityEntry.getValue();
                if (totalItemQuantity.containsKey(menuItemQuantityEntry.getKey())) {
                    int initialQuantity = totalItemQuantity.get(tempItem);
                    addedQuantity = initialQuantity + menuItemQuantityEntry.getValue();
                }
                totalItemQuantity.put(tempItem, addedQuantity);
            }
        }

        return totalItemQuantity;
    }


    public static void processInvoiceListToCSVFile(){
        try {
            // create a list of objects
            List<List<String>> records = new ArrayList<>();

            for(int i =0; i<invoicesList.size();i++){
                List<String> tempList = new ArrayList<>();
                tempList.add(invoicesList.get(i).getLocalDateTime().toString());


                Order thisOrder = invoicesList.get(i).getOrder();
                tempList.add(thisOrder.getDate().toString());
                tempList.add(Integer.toString(thisOrder.getOrderNumber()));
                tempList.add(thisOrder.getCustomer().getName());
                tempList.add(thisOrder.getCustomer().getContactNumber());
                tempList.add(Integer.toString(thisOrder.getGroupSize()));
                tempList.add(thisOrder.getReservationStartTime().toString());
                tempList.add(thisOrder.getReservationEndTime().toString());
                Map<MenuItem,Integer> menuItemList = thisOrder.getItemsOrderedList();
                Map<String,Integer> menuItemStringList = new HashMap<>();
                for(var entry : menuItemList.entrySet()){
                    menuItemStringList.put(entry.getKey().getItemName(),entry.getValue());
                }
                tempList.add(menuItemStringList.toString());
                tempList.add(Integer.toString(thisOrder.getTableNumber()));
                tempList.add(thisOrder.getStaff().getName());
                records.add(tempList);
            }

            // create a writer

            FileWriter writer = new FileWriter("src/com/company/orderInvoices.csv",false);

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
