package com.company.restaurantfunctions;

import java.util.ArrayList;
import java.util.List;

public class InvoiceList {

    public static List<Invoice> invoicesList = new ArrayList<>();

    public static void addInvoice(Invoice invoice){
        if(invoice.isInvoicePaid()){
            invoicesList.add(invoice);
        }else{
            System.out.println("Invoice hasn't been paid yet!");
        }

    }
}
