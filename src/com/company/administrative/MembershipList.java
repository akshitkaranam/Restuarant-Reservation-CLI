package com.company.administrative;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MembershipList {

    private static Map<String,Customer> membersList = new HashMap<>();

    public static void addMember(Customer customer){
        customer.setMember(true);
        membersList.put(customer.contactNumber,customer);
    }



    public static Map<String, Customer> getMembersList() {
        return membersList;
    }
}
