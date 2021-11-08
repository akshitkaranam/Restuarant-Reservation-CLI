package com.company.restaurantfunctions;

import org.w3c.dom.ls.LSOutput;

import java.io.FileWriter;
import java.io.IOException;
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

    public static void testPrint(){
        for(Table table: tableList.values()){

            String stringRequired = "" + table.getTableNumber() +"'" + table.getNumberOfSeats() + "'"
                    + "{";

            List<TableDateSlots> tableDateSlotsList = new ArrayList<>(table.getTableDateSlotsList().values());

            for(TableDateSlots slots : tableDateSlotsList){
                stringRequired = stringRequired + ">" + slots.getDate()  + "=" +  Collections.singletonList(slots.getSlots());
            }
            stringRequired = stringRequired + "};";
            System.out.println(stringRequired);
        }
    }

    public static void processToCSV()  {

        try {
            Set<Order> activeReservationOrderSet = new HashSet<>();
            for (var table : tableList.entrySet()) {
                Map<LocalDate, TableDateSlots> copy = new HashMap<>(table.getValue().getTableDateSlotsList());
                for (var tableDateSlots : copy.entrySet()) {
                    activeReservationOrderSet
                            .addAll(table.getValue().getOrderReservationsByDateForTable(tableDateSlots.getKey()));
                }
            }

            List<Order> activeReservationOrderList = new ArrayList<>(activeReservationOrderSet);

            List<List<String>> records = new ArrayList<>();

            for (int i = 0; i < activeReservationOrderList.size(); i++) {

                if(activeReservationOrderList.get(i) == null){
                    continue;
                }
                List<String> tempList = new ArrayList<>();
                tempList.add(activeReservationOrderList.get(i).getCustomer().getName());
                tempList.add(activeReservationOrderList.get(i).getCustomer().getContactNumber());
                tempList.add(activeReservationOrderList.get(i).getDate().toString());
                tempList.add(Integer.toString(activeReservationOrderList.get(i).getTableNumber()));
                tempList.add(Integer.toString(activeReservationOrderList.get(i).getGroupSize()));
                tempList.add(activeReservationOrderList.get(i).getReservationStartTime().toString());
                tempList.add(activeReservationOrderList.get(i).getReservationEndTime().toString());
                records.add(tempList);
            }

            // create a writer

            FileWriter writer = new FileWriter("src/com/company/orderReservations.csv", false);

            // write all records
            for (List<String> record : records) {
                writer.write(String.join(";", record));
                writer.write("\n");
            }

            //close the writer
            writer.flush();
            writer.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }


}
