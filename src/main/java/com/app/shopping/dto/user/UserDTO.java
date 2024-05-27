package com.app.shopping.dto.user;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "User")
public class UserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    @ApiModelProperty(value = "Hidden")
    private int userId;
    @NotNull
    @NotBlank(message = "User Name  is mandatory")
    private String username;
    @JsonIgnore
    @ApiModelProperty(value = "Hidden")
    private String password;
    @NotBlank(message = "User Email is mandatory")
    @Email(message = "Email should be valid")
    @NotNull
    private String email;


    private String address;

    @JsonIgnore
    @ApiModelProperty(value = "Hidden")
    @Transient
    private boolean statusFlag;
    @JsonIgnore
    @ApiModelProperty(value = "Hidden")
    private String status;

    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    @Pattern(regexp = "\\d{10}", message = "Phone number must contain only 10 digits")
    @NotBlank(message = "User Phone  is mandatory")
    private String phone;


    public UserDTO() {
    }

    public UserDTO(String username, String password, String email, String address, boolean statusFlag, String status, String phone) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.statusFlag = statusFlag;
        this.status = status;
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + username + '\'' +
                ", userEmail='" + email + '\'' +
                ", userAddress='" + address + '\'' +
                ", userStatus='" + status + '\'' +
                ", userPhone='" + phone + '\'' +
                '}';
    }
}


