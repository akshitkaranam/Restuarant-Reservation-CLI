package com.company.administrative;

import java.util.Objects;

public class Staff extends Person {

    public enum JobRole {
        MANAGER,
        WAITER
    }

    private String name;
    private String employeeID;
    private JobRole jobRole;
    private String gender;


    @Override
    public String toString() {
        return this.name;
    }

    public Staff(String name, String employeeId, JobRole role) {
        this.name = name;
        this.employeeID = employeeId;
        this.jobRole = role;
    }

    public String getName() {
        return name;
    }


    public String getGender() {
        return this.gender;
    }

    public void setName(String name) {
        this.name = name;
    }




}
