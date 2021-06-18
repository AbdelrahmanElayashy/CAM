package edu.kit.stateManager.logic.model.permission;

import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class UserLevel implements Serializable {
    private String employeeType;
    private String employeeRank;

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

    public UserLevel(String employeeType, String employeeRank) {
        this.employeeType = employeeType;
        this.employeeRank = employeeRank;
    }
}
