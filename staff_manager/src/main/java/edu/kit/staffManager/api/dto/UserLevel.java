package edu.kit.staffManager.api.dto;

public class UserLevel {
    String employeeType;
    String employeeRank;

    public UserLevel(String employeeType, String employeeRank) {
        this.employeeType = employeeType;
        this.employeeRank = employeeRank;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getEmployeeRank() {
        return employeeRank;
    }

    public void setEmployeeRank(String employeeRank) {
        this.employeeRank = employeeRank;
    }
}
