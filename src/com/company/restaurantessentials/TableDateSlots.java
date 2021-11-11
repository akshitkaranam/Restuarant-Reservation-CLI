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
        this.slots.put(LocalTime.of(9,0),null);
        this.slots.put(LocalTime.of(9,30),null);
        this.slots.put(LocalTime.of(10,0),null);
        this.slots.put(LocalTime.of(10,30),null);
        this.slots.put(LocalTime.of(11,0),null);
        this.slots.put(LocalTime.of(11,30),null);
        this.slots.put(LocalTime.of(12,0),null);
        this.slots.put(LocalTime.of(12,30),null);
        this.slots.put(LocalTime.of(13,0),null);
        this.slots.put(LocalTime.of(13,30),null);
        this.slots.put(LocalTime.of(14,0),null);
        this.slots.put(LocalTime.of(14,30),null);
        this.slots.put(LocalTime.of(15,0),null);
        this.slots.put(LocalTime.of(15,30),null);
        this.slots.put(LocalTime.of(16,0),null);
        this.slots.put(LocalTime.of(16,30),null);
        this.slots.put(LocalTime.of(17,0),null);
        this.slots.put(LocalTime.of(17,30),null);
        this.slots.put(LocalTime.of(18,0),null);
        this.slots.put(LocalTime.of(18,30),null);
        this.slots.put(LocalTime.of(19,0),null);
        this.slots.put(LocalTime.of(19,30),null);
        this.slots.put(LocalTime.of(20,0),null);
        this.slots.put(LocalTime.of(20,30),null);
        this.slots.put(LocalTime.of(21,0),null);
        this.slots.put(LocalTime.of(21,30),null);
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
