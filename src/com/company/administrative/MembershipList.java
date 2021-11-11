package com.company.administrative;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains a static map that contains the information of the Customers who are members
 */

public class MembershipList {

    private static Map<String,Customer> membersList = new HashMap<>();

    /**
     * Adds the relevant Customer to the Members Map
     * @param customer the relevant Customer Object to be added
     */
    public static void addMember(Customer customer){
        customer.setMember(true);
        membersList.put(customer.contactNumber,customer);
    }


    /**
     * returns a Map with Key: Customer's Name (as a String) and Value : Customer object
     * @return Map of Customer Objects
     */
    public static Map<String, Customer> getMembersList() {
        return membersList;
    }
}
