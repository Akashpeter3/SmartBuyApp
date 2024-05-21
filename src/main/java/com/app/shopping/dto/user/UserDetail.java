package com.app.shopping.dto.user;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiParam;

import javax.persistence.*;

@Entity
@Table(name = "UserDetail")
public class UserDetail {
    @Id
    @JsonIgnore
    @ApiParam(value = "Hidden parameter", hidden = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;
    private String name;
    private String email;

    public UserDetail(int userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }


    // Constructor


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



    // toString method to print user details including user type


    @Override
    public String toString() {
        return "UserDetail{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}


