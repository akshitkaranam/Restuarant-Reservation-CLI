package com.company.administrative;

/**
 * This is the Customer class. The Customer has a few basic attributes:
 * - customer ID
 * - name
 * - contact Member
 * - whether he is a member or not
 */

public class Customer extends Person{

    String customerID;
    String name;
    String contactNumber;
    boolean isMember;

    /**
     * Constructor that creates the Customer Object. The customer is not a member by default.
     * @param name name of the Customer
     * @param contactNumber contact number of the Customer
     */
    public Customer(String name, String contactNumber) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.customerID = contactNumber;
        this.isMember = false;
    }

    /**
     *
     * @return customer name
     */
    public String getName() {
        return this.name;
    }

    @Override
    String getGender() {
        return null;
    }

    /**
     *
     * @return if customer is a member
     */
    public boolean isMember() {
        return isMember;
    }

    /**
     *
     * @param member boolean value if customer is a member or not
     */
    public void setMember(boolean member) {
        isMember = member;
    }

    /**
     *
     * @return contact number of the Customer
     */
    public String getContactNumber() {
        return contactNumber;
    }


    @Override
    public String toString() {
        return "" + name + ",contact number=" + contactNumber;
    }
}
