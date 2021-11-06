package com.company.administrative;

import java.util.ArrayList;
import java.util.List;

public class MembershipList {

    private static List<Customer> membersList = new ArrayList<>();

    public static void addMember(Customer customer){
        membersList.add(customer);
    }

    public static List<Customer> getMembersList() {
        return membersList;
    }
}
