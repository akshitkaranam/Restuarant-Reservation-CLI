package com.company.restaurantfunctions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;


public class TableDateSlots {

    LocalDate date;
    Map<LocalTime,Order> slots;

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
        this.slots.put(LocalTime.of(22,00),null);
        this.slots.put(LocalTime.of(22,30),null);
        this.slots.put(LocalTime.of(23,00),null);

    }

    public LocalDate getDate() {
        return date;
    }

    public Map<LocalTime, Order> getSlots() {
        return slots;
    }

    public void reserveSlot(LocalTime time, Order order,int duration) {
        for(int i =0;i<duration;i+=30){
            this.slots.put(time.plusMinutes(i),order);
        }
    }

}
