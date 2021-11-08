package com.company.administrative;


public class Customer extends Person{

    String customerID;
    String name;
    String contactNumber;
    boolean isMember;

    public Customer(String name, String contactNumber) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.customerID = contactNumber;
        this.isMember = false;
    }

    public String getName() {
        return this.name;
    }

    public String getGender() {
        return null;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getContactNumber() {
        return contactNumber;
    }


    @Override
    public String toString() {
        return "" + name + ",contact number=" + contactNumber;
    }
}
