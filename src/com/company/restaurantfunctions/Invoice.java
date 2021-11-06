package com.company.restaurantfunctions;

import com.company.menuItem.MenuItem;

import java.time.LocalDateTime;
import java.util.Map;

public class Invoice {

    private final Order order;
    private double priceBeforeTax = 0.0;
    private double taxPrice = 0.0;
    private double priceAfterTax = 0.0;
    private boolean invoicePaid = false;
    private final LocalDateTime localDateTime;

    public Invoice(Order order) {
        this.order = order;
        localDateTime = LocalDateTime.now();
        calculateFinalPrice();
    }

    private void calculateFinalPrice() {

        Map<MenuItem, Integer> orderItemList = order.getItemsOrderedList();

        for (Map.Entry<MenuItem, Integer> menuItemQuantityEntry : orderItemList.entrySet()) {
            priceBeforeTax += (menuItemQuantityEntry.getKey().getItemPrice() * menuItemQuantityEntry.getValue());
        }
        taxPrice = priceBeforeTax * 0.07;
        priceAfterTax = priceBeforeTax + taxPrice;
    }


    public boolean isInvoicePaid() {
        return invoicePaid;
    }

    public void generateReceipt() {
        invoicePaid = true;
        Map<MenuItem, Integer> orderItemList = order.getItemsOrderedList();
        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println(Restaurant.name);
        System.out.println(Restaurant.address);

        System.out.println("Table Number: " + order.getTableNumber());
        System.out.println(localDateTime);
        System.out.println("Customer: " + order.getCustomer().getName());
        System.out.println("----------------------------------------------");
        for (Map.Entry<MenuItem, Integer> menuItemQuantityEntry : orderItemList.entrySet()) {
            System.out.println(menuItemQuantityEntry.getValue() + " "
                    + menuItemQuantityEntry.getKey().getItemName() + "            "
            +(menuItemQuantityEntry.getKey().getItemPrice()* menuItemQuantityEntry.getValue()));
        }
        System.out.println("----------------------------------------------");
        System.out.println("Subtotal: " + priceBeforeTax);
        System.out.println("Taxes: " + taxPrice);
        System.out.println("----------------------------------------------");
        System.out.println("Total: " + priceAfterTax);
    }


    //    List<Tax> taxList = TaxList.getTaxList();
//        double gstAddedAfterAllOtherTax = 1;
//
//        for (Tax tax : taxList) {
//            if (tax.getTaxName().equals("GST")) {
//                gstAddedAfterAllOtherTax = tax.getPercentageTax();
//            } else {
//                priceAfterTax += (priceBeforeTax * (1.0 + tax.getPercentageTax()));
//            }
//        }


}
