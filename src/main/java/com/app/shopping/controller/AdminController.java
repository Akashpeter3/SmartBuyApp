package com.app.shopping.controller;

import com.app.shopping.constants.AppConstants;
import com.app.shopping.dto.user.UserDTO;
import com.app.shopping.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Api(tags = "Admin Controller")
public class AdminController {

    @Autowired
    private UserService userService;


    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTODetail) {
        String user = userService.addUser(userDTODetail);

        if (AppConstants.USER_EXISTS.equalsIgnoreCase(user)) {
            return new ResponseEntity<>("User " + userDTODetail.getUsername() + " already exists.", HttpStatus.ACCEPTED);
        }
        if (user != null) {
            return new ResponseEntity<>("User " + user + " added.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to add user.", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/removeUser/{userName}")
    public ResponseEntity<String> removeUser(@PathVariable String userName) {
        String response = userService.removeUser(userName);

        if (AppConstants.USER_NOT_EXIST.equalsIgnoreCase(response)) {
            return new ResponseEntity<>("User " + userName + " not exists.", HttpStatus.ACCEPTED);
        }
        if (AppConstants.USER_DELETED.equalsIgnoreCase(response)) {
            return new ResponseEntity<>("User " + userName + " deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to add user.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getUserByName/{userName}")
    public ResponseEntity<?> getUserByName(@PathVariable String userName) {
        UserDTO userDTO = userService.getUserByName(userName);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppConstants.USER_NOT_EXIST);
        }
    }

    @GetMapping("/getUsers")
    public ResponseEntity<?> getUsers() {
        List<UserDTO> userDTOList = userService.getUsers();
        if (userDTOList != null&&!userDTOList.isEmpty()) {
            return ResponseEntity.ok(userDTOList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Users exist");
        }
    }



}
