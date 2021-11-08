package com.company.restaurantfunctions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class Restaurant {
    public static String name = "Mandarin Palace";
    public static String address = "25 Chinatown Street 21, Singapore 123456";

//    public static List<Integer> currentlyOccupiedTables = new ArrayList<>();
    private static Map<Integer, Table> tableList = new HashMap<>();
    private static List<Order> activeOrders = new ArrayList<>();

    public static Map<Integer, Table> getTableList() {
        return tableList;
    }

    public static void addTable(int tableNumber, int numberOfSeats) {
        if (!tableList.containsKey(tableNumber)) {
            tableList.put(tableNumber, new Table(tableNumber, numberOfSeats));
        }
    }

    public static void changeNumberOfSeats(int tableNumber, int newSeatNumber) {
        tableList.get(tableNumber).setNumberOfSeats(newSeatNumber);
    }

    public static void addActiveOrder(Order order){
        activeOrders.add(order);
    }

    public static List<Order> getActiveOrders() {
        return activeOrders;
    }




}
