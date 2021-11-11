package com.company.administrative;

import java.util.ArrayList;

import java.util.List;

public class StaffList {

    private static List<Staff> staffList = new ArrayList<>();

    public static List<Staff> getStaffList() {
        return staffList;
    }

    public static void addStaff(String name, String employeeId, Staff.JobRole jobRole, String gender){
        staffList.add(new Staff(name,employeeId,jobRole,gender));
    }

    public static void deleteUser(String name){
        staffList.remove(name);
    }

}
