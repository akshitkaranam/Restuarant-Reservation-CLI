package com.company.restaurantessentials;

import com.company.administrative.Customer;
import com.company.administrative.Staff;
import com.company.menu.MenuItem;
import com.company.menu.PromotionPackage;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is the main entity that processes the Reservation/Orders made by Customers.
 * This class has the following attributes:
 * <ol>
 *     <li>Customer object
 *     <li>orderNumber
 *     <li>groupSize
 *     <li>tableNumber
 *     <li>date
 *     <li>reservationStartTime
 *     <li>reservationEndTime
 *     <li>reservationIsActive (boolean value)
 *     <li>Staff object
 *     <li> Map of MenuItem as Key and Quantity as value (i.e Laksa,2)
 *     <li>Map of PromotionPackage as Key and Quantity as value
 *     <li>orderIsActive (boolean value)
 *
 * </ol>

 * This class has 2 main function:
 * <ol>
 *     <li> Reservation
 *     <ul><li>Upon Successful reservation, a new Order object is created with an 'active reservation' and 'inactive order'.
 *     </ul>
 *     <li> Ordering
 *     <ul><li>When the Customer successfully checks-in, the order becomes 'active' and the relevant MenuItems and PromotionPackages can then be added accordingly.
 *     </ul>
 * </ol>
 */

public class Order {


    //Reservation
    private final Customer customer;
    private int orderNumber;
    private final int groupSize;
    private final int tableNumber;
    private final LocalDate date;
    private final LocalTime reservationStartTime;
    private final LocalTime reservationEndTime;
    private final boolean reservationIsActive;

    //Order
    private Staff staff;
    private final Map<MenuItem, Integer> itemsOrderedList;
    private final Map<PromotionPackage, Integer> promotionPackageOrderedList;
    private boolean orderIsActive;


    /**
     * This constructor is specifically used when a 'Reservation' is created. This constructor by default \
     * sets 'reservation as active' and 'order as inactive'. This constructor also reads from the orderNumber.csv
     * and creates a new order number accordingly.
     *
     * @param customer             takes in a Customer object.
     * @param groupSize            the size of the group making the reservation
     * @param tableNumber          the table number for the reservation
     * @param date                 the date of the reservation
     * @param reservationStartTime start time of the reservation
     * @param reservationEndTime   end time of the reservation
     */

    public Order(Customer customer, int groupSize, int tableNumber,
                 LocalDate date, LocalTime reservationStartTime, LocalTime reservationEndTime) {
        try {

            // create a reader
            BufferedReader br = Files.newBufferedReader(Paths.get("src/com/company/storeddata/orderNumber.csv"));

            // read the file line by line
            String line;
            while ((line = br.readLine()) != null) {
                this.orderNumber = Integer.parseInt(line) + 1;
            }
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.customer = customer;
        this.groupSize = groupSize;
        this.tableNumber = tableNumber;
        this.date = date;
        this.reservationStartTime = reservationStartTime;
        this.reservationEndTime = reservationEndTime;
        this.reservationIsActive = true;
        this.itemsOrderedList = new HashMap<>();
        this.promotionPackageOrderedList = new HashMap<>();
        this.orderIsActive = false;

        try {

            // create a writer
            FileWriter writer = new FileWriter("src/com/company/storeddata/orderNumber.csv", false);
            // write all records
            writer.write(Integer.toString(this.orderNumber));
            //close the writer
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * This constructor is used when reading from the activeOrder.csv file when re-establishing the order object.
     * @param customer  takes in a Customer object.
     * @param groupSize  the size of the group making the reservation
     * @param tableNumber the table number for the reservation
     * @param date the date of the reservation
     * @param reservationStartTime start time of the reservation
     * @param reservationEndTime end time of the reservation
     * @param orderNumber order number of the order/reservation
     */
    public Order(Customer customer, int groupSize, int tableNumber,
                 LocalDate date, LocalTime reservationStartTime, LocalTime reservationEndTime, int orderNumber) {

        this.customer = customer;
        this.groupSize = groupSize;
        this.tableNumber = tableNumber;
        this.date = date;
        this.reservationStartTime = reservationStartTime;
        this.reservationEndTime = reservationEndTime;
        this.reservationIsActive = true;
        this.itemsOrderedList = new HashMap<>();
        this.promotionPackageOrderedList = new HashMap<>();
        this.orderIsActive = false;
        this.orderNumber = orderNumber;


    }

    /**
     * This constructor is specifically useful when the Order object is created when retrieving information from the
     * orderInvoices.csv file.
     *
     * @param customer             takes in a Customer object.
     * @param orderNumber          the order number of the order
     * @param groupSize            the size of the group making the reservation
     * @param tableNumber          the table number for the reservation
     * @param date                 the date of the reservation
     * @param reservationStartTime start time of the reservation
     * @param reservationEndTime   end time of the reservation
     * @param reservationIsActive  boolean value if reservation is active
     * @param staff                the staff the has served the customer
     * @param orderIsActive        boolean value if the order is active
     */

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
        this.promotionPackageOrderedList = new HashMap<>();
    }


    /**
     * This function allows the staff to add the Customer desired item into the order.
     * i.e. if a customer wants to order 4 Ice Creams Cones
     *
     * @param menuItemToAdd the MenuItem object that needs to be added to the Order
     * @param quantity      the quantity of the MenuItem object that needs to be added.
     */

    public void addMenuItemToOrder(MenuItem menuItemToAdd, int quantity) {
        itemsOrderedList.put(menuItemToAdd, quantity);

    }

    /**
     * This functions allows the staff to edit the item that is already added to the order.
     * The new quantity will override the current quantity.
     * (i.e if the initial Quantity for Ice Cream was '4', and '2' was passed in to this function, then the quantity
     * will be updated to 2
     *
     * @param item        the MenuItem object that needs to be edited
     * @param newQuantity the new quantity required.
     */

    public void editQuantity(MenuItem item, int newQuantity) {
        if (itemsOrderedList.containsKey(item)) {
            if (newQuantity == 0) {
                deleteMenuItem(item);
            }
            itemsOrderedList.put(item, newQuantity);
        }
    }

    /**
     * This function allows the staff to add the Customer desired item into the order.
     *
     * @param promoToAdd the PromotionPackage object that needs to be added to the Order
     *                   * @param quantity the quantity of the MenuItem object that needs to be added.
     */

    /**
     * Adds a PromotionPackage object to the promotionPackageOrderedList
     * @param promoToAdd the promotionPackage object to add
     * @param quantity the quantity needed to add
     */
    public void addPromotionPackageToOrder(PromotionPackage promoToAdd, int quantity) {
        promotionPackageOrderedList.put(promoToAdd, quantity);

    }

    /**
     * This functions allows the staff to edit the item that is already added to the order.
     * * The new quantity will override the current quantity.
     * * (i.e if the initial Quantity for Package 1 was '4', and '2' was passed in to this function, then the quantity
     * * will be updated to 2
     *
     * @param promotionPackage the PromotionPackage object that needs to be edited
     * @param newQuantity      the new quantity required.
     */

    public void editQuantity(PromotionPackage promotionPackage, int newQuantity) {
        if (promotionPackageOrderedList.containsKey(promotionPackage)) {
            if (newQuantity == 0) {
                deletePromotionPackage(promotionPackage);
            }
            promotionPackageOrderedList.put(promotionPackage, newQuantity);
        }
    }

    /**
     * Deletes the passed MenuItem from the Order
     * @param menuItem the MenuItem object that needs to be removed
     */

    public void deleteMenuItem(MenuItem menuItem) {
        itemsOrderedList.remove(menuItem);
    }

    /**
     * Deletes the passed PromotionPackage from the Order
     *
     * @param promoPack the PromotionPackage object that needs to be removed
     */
    public void deletePromotionPackage(PromotionPackage promoPack) {
        itemsOrderedList.remove(promoPack);
    }


    /**
     * Sets the staff that is managing the order.
     *
     * @param staff the Staff Object that refers to the staff member that is managing the order
     */
    public void setStaff(Staff staff) {
        this.staff = staff;
    }


    /**
     * returns the Staff Object
     *
     * @return the Staff object that refers to the staff member that is managing the order
     */
    public Staff getStaff() {
        assert staff != null;
        return staff;
    }

    /**
     * Returns the order number
     *
     * @return orderNumber
     */

    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * This returns the HashMap of the ordered MenuItems and the respective quantities.
     *
     * @return the HashMap of MenuItem and the quantity of the items the customer has ordered
     */
    public Map<MenuItem, Integer> getItemsOrderedList() {
        return itemsOrderedList;
    }

    /**
     * Returns a boolean value ot check if reservation is active
     *
     * @return if reservation is active, return true else return false
     */
    public boolean isReservationIsActive() {
        return reservationIsActive;
    }


    /**
     * Returns a boolean value ot check if order is active
     *
     * @return if order is active, return true else return false
     */
    public boolean isOrderIsActive() {
        return orderIsActive;
    }

    /**
     * Sets the boolean variable orderIsActive
     * @param orderIsActive boolean value if order is active
     */
    public void setOrderIsActive(boolean orderIsActive) {
        this.orderIsActive = orderIsActive;
    }

    /**
     * Returns an int value of the table number
     *
     * @return table number
     */
    public int getTableNumber() {
        return tableNumber;
    }

    /**
     * Returns the Customer object
     *
     * @return Customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Returns the start time of the reservation
     *
     * @return reservationStartTime
     */
    public LocalTime getReservationStartTime() {
        return reservationStartTime;
    }

    /**
     * Returns the start time of the reservation
     *
     * @return reservationEndTime
     */
    public LocalTime getReservationEndTime() {
        return reservationEndTime;
    }

    /**
     * Returns the size of the group.
     *
     * @return groupSize
     */
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
                ", activeOrder=" + orderIsActive +
                '}';
    }


    /**
     * This returns the HashMap of the ordered PromotionPackage and the respective quantities.
     *
     * @return the HashMap of MenuItem and the quantity of the items the customer has ordered
     */

    public Map<PromotionPackage, Integer> getPromotionPackageOrderedList() {
        return promotionPackageOrderedList;
    }

    /**
     * Returns the Date of the reservation
     *
     * @return date
     */
    public LocalDate getDate() {
        return date;
    }

}
