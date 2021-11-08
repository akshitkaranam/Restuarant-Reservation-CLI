package com.company.restaurantfunctions;

import com.company.administrative.Customer;
import com.company.administrative.Staff;
import com.company.menuItem.MenuItem;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {

    private static int totalOrderNumber = 1000000;

    //Reservation
    private Customer customer;
    private int orderNumber;
    private int groupSize;
    private int tableNumber;
    private LocalDate date;
    private LocalTime reservationStartTime;
    private LocalTime reservationEndTime;
    private boolean reservationIsActive;

    //Order
    private Staff staff;
    private Map<MenuItem, Integer> itemsOrderedList;
    private boolean orderIsActive;


    public Order(Customer customer, int groupSize, int tableNumber,
                 LocalDate date, LocalTime reservationStartTime, LocalTime reservationEndTime) {
        this.customer = customer;
        this.orderNumber = totalOrderNumber++;
        this.groupSize = groupSize;
        this.tableNumber = tableNumber;
        this.date = date;
        this.reservationStartTime = reservationStartTime;
        this.reservationEndTime = reservationEndTime;
        this.reservationIsActive = true;
        this.itemsOrderedList = new HashMap<>();
        this.orderIsActive = false;

    }

    public Order(Customer customer, int orderNumber, int groupSize, int tableNumber, LocalDate date,
                 LocalTime reservationStartTime, LocalTime reservationEndTime, boolean reservationIsActive,
                 Staff staff, boolean orderIsActive) {
        this.customer = customer;
        this.orderNumber = orderNumber;
        this.groupSize = groupSize;
        this.tableNumber = tableNumber;
        this.date = date;
        this.reservationStartTime = reservationStartTime;
        this.reservationEndTime = reservationEndTime;
        this.reservationIsActive = reservationIsActive;
        this.staff = staff;
        this.orderIsActive = orderIsActive;
        this.itemsOrderedList = new HashMap<>();
    }

    public void addMenuItemToOrder(MenuItem foodToAdd, int quantity) {
        itemsOrderedList.put(foodToAdd, quantity);

    }

    public void editQuantity(MenuItem item, int newQuantity) {
        if (itemsOrderedList.containsKey(item)) {
            if (newQuantity == 0) {
                deleteMenuItem(item);
            }
            itemsOrderedList.put(item, newQuantity);
        }
    }

    public void deleteMenuItem(MenuItem menuItem) {
        itemsOrderedList.remove(menuItem);
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Staff getStaff() {
        return staff;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public Map<MenuItem, Integer> getItemsOrderedList() {
        return itemsOrderedList;
    }


    public boolean isReservationIsActive() {
        return reservationIsActive;
    }

    public void setReservationIsActive(boolean reservationIsActive) {
        this.reservationIsActive = reservationIsActive;
    }

    public boolean isOrderIsActive() {
        return orderIsActive;
    }

    public void setOrderIsActive(boolean orderIsActive) {
        this.orderIsActive = orderIsActive;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalTime getReservationStartTime() {
        return reservationStartTime;
    }

    public LocalTime getReservationEndTime() {
        return reservationEndTime;
    }

    public int getGroupSize() {
        return groupSize;
    }

    @Override
    public String toString() {
        return "{" +
                "customer=" + customer +
                ", groupSize=" + groupSize +
                ", tableNumber=" + tableNumber +
                ", date=" + date +
                ", reservationStartTime=" + reservationStartTime +
                ", reservationEndTime=" + reservationEndTime +
                '}';
    }



    public String orderToString() {
        return "{" +
                "customer=" + customer +
                ", orderNumber=" + orderNumber +
                ", groupSize=" + groupSize +
                ", tableNumber=" + tableNumber +
                ", date=" + date +
                ", reservationStartTime=" + reservationStartTime +
                ", reservationEndTime=" + reservationEndTime +
                ", reservationIsActive=" + reservationIsActive +
                ", staff=" + staff +
                ", itemsOrderedList=" + itemsOrderedList +
                ", orderIsActive=" + orderIsActive +
                '}';
    }


    public LocalDate getDate() {
        return date;
    }


}
