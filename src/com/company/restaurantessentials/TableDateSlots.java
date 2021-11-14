package com.company.restaurantessentials;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * This class refers to the entity where a Table has slots with 30 minutes intervals from 9am to 10pm
 * (This is the operating hours of the restaurant). These slots are filled up accordingly by adding a new Order object
 * when a reservation is created wherever possible.
 * The new Order object created has "an active reservation" but "an inactive order".
 * Once an Order is checked-in it becomes an 'active order'
 * Once an Order has been checked-out, the Order object will be removed from the respective time slots.
 */



public class TableDateSlots {

    LocalDate date;
    Map<LocalTime,Order> slots;

    /**
     * Constructor takes in the date these slots are set up for
     * @param date the LocalDate of the Slots
     */

    public TableDateSlots(LocalDate date) {
        this.date = date;
        this.slots = new LinkedHashMap<>();

        LocalTime restaurantOpeningTime = Restaurant.getOpeningTime();
        LocalTime restaurantClosingTime = Restaurant.getClosingTime();

        while(restaurantOpeningTime.isBefore(restaurantClosingTime)){
            this.slots.put(restaurantOpeningTime,null);
            restaurantOpeningTime = restaurantOpeningTime.plusMinutes(30);
        }
    }

    /**
     *
     * @return the date of the slots
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * This returns a hashmap with key being the timeslot and the value being the Order object.
     * @return HashMap of the order objects at each particular time slot
     */
    public Map<LocalTime, Order> getSlots() {
        return slots;
    }

    /**
     *
     * @param time the time for reservation
     * @param order the Order object that has been populated with the necessary details
     * @param duration the duration of the reservation in minutes
     */
    public void reserveSlot(LocalTime time, Order order,int duration) {
        for(int i =0;i<duration;i+=30){
            this.slots.put(time.plusMinutes(i),order);
        }
    }

}
