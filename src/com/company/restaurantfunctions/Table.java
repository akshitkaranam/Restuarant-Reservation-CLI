package com.company.restaurantfunctions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Table {

    private final int tableNumber;
    private int numberOfSeats;
    private Map<LocalDate, TableDateSlots> tableDateSlotsList;


    public Table(int tableNumber, int numberOfSeats) {
        this.tableNumber = tableNumber;
        this.numberOfSeats = numberOfSeats;
        tableDateSlotsList = new HashMap<>();
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Map<LocalDate, TableDateSlots> getTableDateSlotsList() {
        return tableDateSlotsList;
    }

    public ArrayList<Order>  getOrderReservationsByDateForTable(LocalDate date){
        if(tableDateSlotsList.containsKey(date)){
            return new ArrayList<>(this.getTableDateSlotsList().get(date).getSlots().values());
        }
        return new ArrayList<>();
    }




}
