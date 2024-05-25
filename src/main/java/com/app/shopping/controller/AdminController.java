package com.app.shopping.controller;

import com.app.shopping.constants.AppConstants;
import com.app.shopping.dto.user.User;
import com.app.shopping.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody User userDetail) {
        String user = adminService.addUser(userDetail);

        if (user.equalsIgnoreCase(AppConstants.USER_EXISTS)){
            return new ResponseEntity<>(AppConstants.USER_EXISTS, HttpStatus.BAD_REQUEST);
        }
        if (user != null) {
            return new ResponseEntity<>(" user "+user+" added ", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to add user", HttpStatus.BAD_REQUEST);
        }
    }
}
