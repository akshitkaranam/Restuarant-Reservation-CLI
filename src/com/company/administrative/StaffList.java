package com.company.administrative;

import java.util.HashMap;
import java.util.Map;

public class StaffList {

    private static Map<String, Staff> staffList = new HashMap<>();

    public static Map<String, Staff> getStaffList() {
        return staffList;
    }

    public static boolean addUser(String name, String employeeId, Staff.JobRole jobRole){
        if(!staffList.containsKey(name)){
            staffList.put(name,new Staff(name,employeeId,jobRole));
            return true;
        }else{
            return false;
        }
    }

    public static boolean deleteUser(String name){
        if(staffList.containsKey(name)){
            staffList.remove(name);
            return true;
        }
        return false;
    }

}
