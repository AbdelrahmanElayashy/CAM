package edu.kit.staffManager.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class UserDTO {

    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private String employeeType;
    private String employeeRank;

    public UserDTO(UUID userId,
                   @JsonProperty("firstName") String firstName,
                   @JsonProperty("lastName") String lastName,
                   @JsonProperty("email") String email,
                   @JsonProperty("userName") String userName,
                   @JsonProperty("password") String password,
                   @JsonProperty("employeeType") String employeeType,
                   @JsonProperty("employeeRank") String employeeRank) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.employeeType = employeeType;
        this.employeeRank = employeeRank;
    }

    public UserDTO(String firstName, String lastName, String email, String userName, String password, String employeeType, String employeeRank) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.employeeType = employeeType;
        this.employeeRank = employeeRank;
    }

    public UserDTO(String userName, String password, String employeeType, String employeeRank) {
        this.userName = userName;
        this.password = password;
        this.employeeType = employeeType;
        this.employeeRank = employeeRank;
    }

    public UserDTO(UUID userId, String userName, String password, String employeeType, String employeeRank) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.employeeType = employeeType;
        this.employeeRank = employeeRank;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
