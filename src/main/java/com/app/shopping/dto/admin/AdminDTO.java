package com.app.shopping.dto.admin;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "Admin")
public class AdminDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    @ApiModelProperty(value = "Hidden")
    private int adminId;
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


    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    @Pattern(regexp = "\\d{10}", message = "Phone number must contain only 10 digits")
    @NotBlank(message = "User Phone  is mandatory")
    private String phone;

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
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


}
