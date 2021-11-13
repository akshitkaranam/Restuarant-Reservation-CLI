package com.company.restaurantessentials;

import com.company.menuItem.MenuItem;
import com.company.menuItem.PromotionPackage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

/**
 * This is class is the entity that is created when a Customer checks-out from the restaurant.
 */

public class Invoice implements Comparable<Invoice> {

    private final Order order;
    private double priceBeforeTax = 0.0;
    private double taxPrice = 0.0;
    private double priceAfterTax = 0.0;
    private double memberDiscount = 0.0;
    private boolean invoicePaid = false;
    private final LocalDateTime localDateTime;


    /**
     * This constructor takes in the Order object as a parameter. It also calculates the final price the customer
     * has to pay based on their order details (added MenuItems/Promotion Packages).
     * This constructor is useful when the Customer is checking-out from the restaurant
     *
     * @param order the relevant Order object
     */

    public Invoice(Order order) {
        this.order = order;
        localDateTime = LocalDateTime.now();
        calculateFinalPrice();
    }

    /**
     * This constructor is useful when the invoice object is being re-created when reading from the CSV-file (orderInvoices.csv)
     *
     * @param order         the relevant Order object
     * @param localDateTime the time/date this invoice was originally created.
     */

    public Invoice(Order order, LocalDateTime localDateTime) {
        this.order = order;
        this.localDateTime = localDateTime;
        invoicePaid = true;
        calculateFinalPrice();

    }

    /**
     * Calculates the final price of the Order
     */
    private void calculateFinalPrice() {
        boolean customerIsMember = order.getCustomer().isMember();
        Map<MenuItem, Integer> orderItemList = order.getItemsOrderedList();
        Map<PromotionPackage, Integer> orderPackageList = order.getPromotionPackageOrderedList();

        for (Map.Entry<MenuItem, Integer> menuItemQuantityEntry : orderItemList.entrySet()) {
            priceBeforeTax += (menuItemQuantityEntry.getKey().getItemPrice() * menuItemQuantityEntry.getValue());
        }

        for (Map.Entry<PromotionPackage, Integer> promotionPackageQuantityEntry : orderPackageList.entrySet()) {
            priceBeforeTax += (promotionPackageQuantityEntry.getKey().getPackagePrice()
                    * promotionPackageQuantityEntry.getValue());
        }

        if (customerIsMember) {
            //5% discount for members
            memberDiscount = priceBeforeTax * 0.05;
        }

        taxPrice = (priceBeforeTax - memberDiscount) * 0.07;
        priceAfterTax = priceBeforeTax - memberDiscount + taxPrice;
    }


    /**
     * Check if the invoice is paid
     *
     * @return boolean value of invoice is paid
     */
    public boolean isInvoicePaid() {
        return invoicePaid;
    }

    /**
     * Sets invoicePaid to true and generates a receipt and adds this invoice object into the static list present in the InvoiceList Class
     */
    public void generateReceipt() {

        invoicePaid = true;
        Map<MenuItem, Integer> orderItemList = order.getItemsOrderedList();
        Map<PromotionPackage, Integer> orderPackageList = order.getPromotionPackageOrderedList();
        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println(Restaurant.name);
        System.out.println(Restaurant.address);

        System.out.println("Table Number: " + order.getTableNumber());
        System.out.println(localDateTime);
        System.out.println("Customer: " + order.getCustomer().getName());
        System.out.println("Served by: " + order.getStaff().getName());
        System.out.println("----------------------------------------------");

        for (Map.Entry<MenuItem, Integer> menuItemQuantityEntry : orderItemList.entrySet()) {
            System.out.println(menuItemQuantityEntry.getValue() + " "
                    + menuItemQuantityEntry.getKey().getItemName() + "\t\t\t\t\t"
                    + (menuItemQuantityEntry.getKey().getItemPrice() * menuItemQuantityEntry.getValue()));
        }

        for (Map.Entry<PromotionPackage, Integer> promotionPackageQuantityEntry : orderPackageList.entrySet()) {
            System.out.println(promotionPackageQuantityEntry.getValue() + " "
                    + promotionPackageQuantityEntry.getKey().getPackageName() + "\t\t\t\t\t"
                    + (promotionPackageQuantityEntry.getKey().getPackagePrice() * promotionPackageQuantityEntry.getValue()));
            for (MenuItem mItem : promotionPackageQuantityEntry.getKey().getPromotionPackage()) {
                System.out.println("\n" + mItem.getItemName());
            }
        }


        System.out.println("----------------------------------------------");
        System.out.println("Subtotal: " + priceBeforeTax);
        if (order.getCustomer().isMember()) {
            System.out.println("Member's Discount: -" + memberDiscount);
        }
        System.out.println("Taxes: " + taxPrice);
        System.out.println("----------------------------------------------");
        System.out.println("Total: " + priceAfterTax);
        System.out.println("----------------------------------------------");
        System.out.println();
        System.out.println();
        InvoiceList.addInvoice(this);
    }

    /**
     * @return the Time/Date the invoice was generated
     */
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    @Override
    public int compareTo(Invoice in) {
        return this.localDateTime.compareTo(in.localDateTime);
    }

    /**
     * @return the Order object in the invoice
     */
    public Order getOrder() {
        return order;
    }

    /**
     * @return LocalDate
     */
    public LocalDate getDate() {
        return localDateTime.toLocalDate();
    }

    /**
     * @return Month
     */
    public Month getMonth() {
        return localDateTime.getMonth();
    }


    /**
     * @return priceAfterTax;
     */
    public double getPriceAfterTax() {
        return priceAfterTax;
    }
}
