package com.company.restaurantessentials;

import java.time.LocalDate;
import java.util.*;

/**
 * This class refers to the Table entity. This Table contains the following information:
 * 1. The Table number
 * 2. The number of seats the table can accommodate
 * 3. Map with LocalDate as the key and TableDateSlots as the value.
 * -> So, for example, this is Table Number 7 and to access the TableDateSlots for this table on a particular date the HashMap can be used
 *
 */



public class Table {

    private final int tableNumber;
    private int numberOfSeats;
    private Map<LocalDate, TableDateSlots> tableDateSlotsList;

    /**
     * Constructor for the Table object
     * @param tableNumber the number of the table
     * @param numberOfSeats the number of seats the table can accommodate
     */
    public Table(int tableNumber, int numberOfSeats) {
        this.tableNumber = tableNumber;
        this.numberOfSeats = numberOfSeats;
        this.tableDateSlotsList = new HashMap<>();
    }

    /**
     *
     * @return Table Number
     */
    public int getTableNumber() {
        return tableNumber;
    }

    /**
     *
     * @return number of seats the Table can accommodate
     */
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * Return a Map that contains Date as the Key and the TableDateSlots as the Value
     * @return Map that contains Date as the Key and the TableDateSlots as the Value
     */
    public Map<LocalDate, TableDateSlots> getTableDateSlotsList() {
        return tableDateSlotsList;
    }

    /**
     * Returns a list of Order objects that are created for the purpose of reservation by Date.
     * (,i.e it returns a list of Order objects with 'Active Reservation')
     * @param date date of the reservation
     * @return List of Order objects
     */
    public ArrayList<Order>  getOrderReservationsByDateForTable(LocalDate date){
        if(tableDateSlotsList.containsKey(date)){
            return new ArrayList<>(this.getTableDateSlotsList().get(date).getSlots().values());
        }
        return new ArrayList<>();
    }

}
