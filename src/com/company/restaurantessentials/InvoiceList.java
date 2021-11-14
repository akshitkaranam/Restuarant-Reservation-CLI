package com.company.restaurantessentials;

import com.company.menu.MenuItem;
import com.company.menu.PromotionPackage;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.List;

/**
 * This class is mainly used to generate sales reports. This contains a static list of Invoices from all time.
 */

public class InvoiceList {


    private static final List<Invoice> invoicesList = new ArrayList<>();

    /**
     * @param invoice the invoice object needed to be added
     */
    public static void addInvoice(Invoice invoice) {
        if (invoice.isInvoicePaid()) {
            invoicesList.add(invoice);
        } else {
            System.out.println("Invoice hasn't been paid yet!");
        }
    }

    /**
     * Get sales report by a given day
     * <ol>
     *     <li>Total Revenue
     *     <li>Quantity of MenuItems/ Promotion Packages Sold
     *
     * </ol>
     *
     * @param date the relevant date that is being queried
     */
    public static void salesReportByDay(LocalDate date) {
        List<Invoice> relevantInvoices = new ArrayList<>(invoicesList);
        relevantInvoices.removeIf(n -> !n.getDate().equals(date));
        int option = 1;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("""
                    Choose an option:
                    ================================
                    |1. Get Total Revenue |
                    |2. Get Sales of Individual Items and Packages|
                    |3. Quit|
                    ==================================""");
            option = sc.nextInt();
            switch (option) {
                case 1 -> {
                    int totalRevenue = calculateTotalRevenue(relevantInvoices);
                    System.out.println("Total Revenue for Today is: " + totalRevenue);
                }
                case 2 -> {
                    Map<MenuItem, Integer> totalItemQuantity = getMenuItemQuantity(relevantInvoices);
                    for (Map.Entry<MenuItem, Integer> menuItemQuantityEntry : totalItemQuantity.entrySet()) {
                        System.out.println(menuItemQuantityEntry.getKey() + ": " + menuItemQuantityEntry.getValue());
                    }
                }
                case 3 -> System.out.println("Quitting, going back to Main Menu!");
                default -> System.out.println("Choose option (1-3):");
            }

        } while (option != 3);
    }


    /**
     * Get sales report by a given Month
     * <ol>
     *     <li>Total Revenue
     *     <li> Quantity of MenuItems/ Promotion Packages Sold
     * </ol>
     *
     * @param month the relevant Month that is being queried
     */
    public static void salesReportByMonth(Month month) {
        List<Invoice> relevantInvoices = new ArrayList<>(invoicesList);
        relevantInvoices.removeIf(n -> !n.getMonth().equals(month));
        int option = 1;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("""
                    Choose an option:
                    ================================
                    |1. Get Total Revenue |
                    |2. Get Sales of Individual Items|
                    |3. Quit|
                    ==================================""");
            option = sc.nextInt();
            switch (option) {
                case 1 -> {
                    int totalRevenue = calculateTotalRevenue(relevantInvoices);
                    System.out.println("Total Revenue for Month is: " + totalRevenue);
                }
                case 2 -> {
                    Map<MenuItem, Integer> totalItemQuantity = getMenuItemQuantity(relevantInvoices);
                    Map<PromotionPackage, Integer> totalPromoQuantity = getPromoPackageQuantity(relevantInvoices);
                    System.out.println("Total Item and Package Sales for Month is: ");
                    for (Map.Entry<MenuItem, Integer> menuItemQuantityEntry : totalItemQuantity.entrySet()) {
                        System.out.println(menuItemQuantityEntry.getKey() + ": " + menuItemQuantityEntry.getValue());
                    }
                    for (Map.Entry<PromotionPackage, Integer> promoPackageQuantityEntry : totalPromoQuantity.entrySet()) {
                        System.out.println(promoPackageQuantityEntry.getKey() + ": " + promoPackageQuantityEntry.getValue());
                    }
                }
                case 3 -> System.out.println("Quitting, going back to Main Menu!");
                default -> System.out.println("Choose option (1-3):");
            }

        } while (option != 3);
    }

    /**
     * Calculates the total revenue given the list of Relevant Invoices
     *
     * @param relevantInvoices List of invoices objects that need to be calculated
     * @return total revenue
     */
    private static int calculateTotalRevenue(List<Invoice> relevantInvoices) {
        int totalRevenue = 0;
        for (Invoice in : relevantInvoices) {
            totalRevenue += in.getPriceAfterTax();
        }

        return totalRevenue;
    }

    /**
     * This calculates the total quantity for each MenuItem
     *
     * @param relevantInvoices List of Invoice of objects that need to be calculated
     * @return total quantity of MenuItems
     */

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

    /**
     * This calculates the total quantity for each PromotionPackage
     *
     * @param relevantInvoices List of Invoice of objects that need to be calculated
     * @return
     */
    private static Map<PromotionPackage, Integer> getPromoPackageQuantity(List<Invoice> relevantInvoices) {
        Map<PromotionPackage, Integer> totalPromoQuantity = new HashMap<>();

        for (Invoice in : relevantInvoices) {
            Map<PromotionPackage, Integer> rawMap = in.getOrder().getPromotionPackageOrderedList();
            for (Map.Entry<PromotionPackage, Integer> promoPackQuantityEntry : rawMap.entrySet()) {

                PromotionPackage tempPromo = promoPackQuantityEntry.getKey();
                int addedQuantity = promoPackQuantityEntry.getValue();
                if (totalPromoQuantity.containsKey(promoPackQuantityEntry.getKey())) {
                    int initialQuantity = totalPromoQuantity.get(tempPromo);
                    addedQuantity = initialQuantity + promoPackQuantityEntry.getValue();
                }
                totalPromoQuantity.put(tempPromo, addedQuantity);
            }
        }

        return totalPromoQuantity;
    }


    /**
     * Writes the information from the invoices list to a CSV File: orderInvoices.csv
     */
    public static void processInvoiceListToCSVFile() {
        try {
            // create a list of objects
            List<List<String>> records = new ArrayList<>();

            for (Invoice invoice : invoicesList) {
                List<String> tempList = new ArrayList<>();
                tempList.add(invoice.getLocalDateTime().toString());


                Order thisOrder = invoice.getOrder();
                tempList.add(thisOrder.getDate().toString());
                tempList.add(Integer.toString(thisOrder.getOrderNumber()));
                tempList.add(thisOrder.getCustomer().getName());
                tempList.add(thisOrder.getCustomer().getContactNumber());
                tempList.add(Integer.toString(thisOrder.getGroupSize()));
                tempList.add(thisOrder.getReservationStartTime().toString());
                tempList.add(thisOrder.getReservationEndTime().toString());

                Map<MenuItem, Integer> menuItemList = thisOrder.getItemsOrderedList();
                Map<String, Integer> menuItemStringList = new HashMap<>();
                for (var entry : menuItemList.entrySet()) {
                    menuItemStringList.put(entry.getKey().getItemName(), entry.getValue());
                }
                tempList.add(menuItemStringList.toString());

                Map<PromotionPackage, Integer> promoPackList = thisOrder.getPromotionPackageOrderedList();
                Map<String, Integer> promoPackStringList = new HashMap<>();

                for (var entry : promoPackList.entrySet()) {
                    promoPackStringList.put(entry.getKey().getPackageName(), entry.getValue());
                }
                tempList.add(promoPackStringList.toString());

                tempList.add(Integer.toString(thisOrder.getTableNumber()));
//                assert thisOrder.getStaff() != null;
                tempList.add(thisOrder.getStaff().getName());
                records.add(tempList);
            }

            // create a writer

            FileWriter writer = new FileWriter("src/com/company/storeddata/orderInvoices.csv", true);

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
