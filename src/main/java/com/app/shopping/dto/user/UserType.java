package com.app.shopping.dto.user;


public class UserType {

    public static final String ADMIN = "ADMIN";
    public static final String STAFF = "STAFF";
    public static final String NORMAL = "NORMAL";

    private String type;

    // Constructor
    public UserType(String type) {
        this.type = type;
    }

    // Getter and Setter
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // toString method to print the user type
    @Override
    public String toString() {
        return "UserType{" +
                "type='" + type + '\'' +
                '}';
    }
}
