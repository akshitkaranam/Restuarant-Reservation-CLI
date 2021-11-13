package com.company.administrative;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains a static map that contains the information of the Customers who are members
 */

public class MembershipList {

    private static final Map<String, Customer> membersList = new HashMap<>();

    /**
     * Adds the relevant Customer to the Members Map
     *
     * @param customer the relevant Customer Object to be added
     */
    public static void addMember(Customer customer) {
        customer.setMember(true);
        membersList.put(customer.contactNumber, customer);
    }


    /**
     * @return Map of Customer's Name:Customer Objects
     */
    public static Map<String, Customer> getMembersList() {
        return membersList;
    }
}
