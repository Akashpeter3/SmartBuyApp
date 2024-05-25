package com.app.shopping.dto.user;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "User")
public class User {
    @Id
    @JsonIgnore
    @ApiParam(value = "Hidden parameter", hidden = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;
    @NotNull
    @NotBlank(message = "User Name  is mandatory")
    private String userName;
    @JsonIgnore
    @ApiModelProperty(value = "Hidden")
    private String userPassword;
    @NotBlank(message = "User Email is mandatory")
    @Email(message = "Email should be valid")
    @NotNull
    private String userEmail;


    private String userAddress;

    @JsonIgnore
    @ApiModelProperty(value = "Hidden")
    @Transient
    private boolean statusFlag;
    @JsonIgnore
    @ApiModelProperty(value = "Hidden")
    private String userStatus;

    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    @Pattern(regexp = "\\d{10}", message = "Phone number must contain only 10 digits")
    @NotBlank(message = "User Phone  is mandatory")
    private String userPhone;


    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public boolean getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(boolean statusFlag) {
        this.statusFlag = statusFlag;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", userPhone='" + userPhone + '\'' +
                '}';
    }
}


