package com.company.administrative;

/**
 * This is the Customer class. The Customer has a few basic attributes:
 * <ol>
 *  <li>customer ID
 *  <li>name
 *  <li>contact Member
 *  <li>membership status
 * </ol>
 */

public class Customer extends Person {

    String customerID;
    String name;
    String contactNumber;
    boolean isMember;

    /**
     * Constructor that creates the Customer Object. The customer is not a member by default.
     *
     * @param name          name of the Customer
     * @param contactNumber contact number of the Customer
     */
    public Customer(String name, String contactNumber) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.customerID = contactNumber;
        this.isMember = false;
    }

    /**
     * Returns the name of the customer
     *
     * @return name
     */
    public String getName() {
        return this.name;
    }

    @Override
    String getGender() {
        return null;
    }

    /**
     * Returns a boolean value indicating if this Person object is a member
     *
     * @return gender
     */
    public boolean isMember() {
        return isMember;
    }

    /**
     * Sets a boolean value indicating whether this Person object is a member
     *
     * @param member boolean value if customer is a member or not
     */
    public void setMember(boolean member) {
        isMember = member;
    }

    /**
     * Returns contact number of this Person object
     *
     * @return contactNumber
     */
    public String getContactNumber() {
        return contactNumber;
    }


    @Override
    public String toString() {
        return "" + name + ",contact number=" + contactNumber;
    }
}
