package com.app.shopping.controller;


import com.app.shopping.dto.admin.AdminDTO;
import com.app.shopping.service.AdminService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = " Admin Login Controller")
public class AdminLoginController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/adminLogin")
    ResponseEntity<?>adminLogin(@RequestBody  AdminDTO adminDTO){
     boolean isAuthenticated =   adminService.adminLogin(adminDTO);

     if(isAuthenticated){
         return ResponseEntity.status(HttpStatus.OK).body("Admin Logged in successfully");
     }
     else {
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
     }

    }
}
