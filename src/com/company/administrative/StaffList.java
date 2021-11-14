package com.company.administrative;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is entity that contains information of the Staff Objects who are staff members of the Restaurant, in a list
 * known as staffList.
 */

public class StaffList {

    private static final List<Staff> staffList = new ArrayList<>();

    /**
     * Returns the List of Staff objects for the staffUsers in the restaurant
     *
     * @return list of Staff Objects
     */
    public static List<Staff> getStaffList() {
        return staffList;
    }

    /**
     * Adds a new Staff object into the staffList
     *
     * @param name       name of the staffUser
     * @param employeeId employeeID of the staffUser
     * @param jobRole    jobRole of the staffUser
     * @param gender     gender of the staffUser
     */
    public static void addStaff(String name, String employeeId, Staff.JobRole jobRole, String gender) {
        staffList.add(new Staff(name, employeeId, jobRole, gender));
    }

    /**
     * Delete the user from the staffList given the name of the user
     *
     * @param name name of the staffUser
     */
    public static void deleteUser(String name) {
        staffList.removeIf(staff -> Objects.equals(staff.getName(), name));
    }
}
