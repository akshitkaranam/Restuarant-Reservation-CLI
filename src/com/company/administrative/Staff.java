package com.company.administrative;

import java.util.Objects;

public class Staff extends Person{

    public enum JobRole{
        MANAGER,
        CASHIER
    }

    private String name;
    private String password;
    private String employeeID;
    private JobRole jobRole;
    private String gender;


    @Override
    public String toString() {
        return this.name;
    }

    public Staff(String name, String password, JobRole role) {
        this.name = name;
        this.password = password;
       jobRole = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkPassword(String password){
        return password.equals(this.password);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return name.equals(staff.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password);
    }
}
