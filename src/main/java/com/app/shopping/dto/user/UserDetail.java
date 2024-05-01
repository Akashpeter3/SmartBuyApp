package com.app.shopping.dto.user;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "UserDetail")
public class UserDetail {
    private int userId;
    private String name;
    private String email;
    private UserType userType; // One-to-One mapping

    // Constructor
    public UserDetail(int userId, String name, String email, UserType userType) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.userType = userType;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    // toString method to print user details including user type
    @Override
    public String toString() {
        return "UserDetail{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", userType=" + userType +
                '}';
    }
}


