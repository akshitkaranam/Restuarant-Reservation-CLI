package com.company.administrative;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is entity that contains information of the Customer Objects who are members.
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


    /** Returns HashMap with Key being the customer's name (String) and the value being the Customer Object
     * @return Map of Customer's Name (String), Person Object
     */
    public static Map<String, Customer> getMembersList() {
        return membersList;
    }
}
