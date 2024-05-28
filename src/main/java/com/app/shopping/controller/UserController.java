package com.app.shopping.controller;


import com.app.shopping.constants.AppConstants;
import com.app.shopping.dto.user.UserDTO;
import com.app.shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {



    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTODetail) {
        String user = userService.registerUser(userDTODetail);
        if (AppConstants.USER_EXISTS.equalsIgnoreCase(user)) {
            return new ResponseEntity<>("User " + userDTODetail.getUsername() + " already exists.", HttpStatus.ACCEPTED);
        }
        if (user != null) {
            return new ResponseEntity<>("User " + user + " added.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to add user.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?>loginUser(String username, String password){
     boolean isAuthenticated = userService.loginUser(username, password);
     if (isAuthenticated){
         return new ResponseEntity<>("User Logged In SuccessFully", HttpStatus.OK);
     }else {
         return new ResponseEntity<>("User Not Logged In", HttpStatus.UNAUTHORIZED);
     }
    }

    @PutMapping("/resetPassWord")
    public ResponseEntity<?>resetPassWord(String username ,String oldPassWord,String newPassWord){
        boolean isReset= userService.resetPassword(username,oldPassWord,newPassWord);
        if (isReset){
            return new ResponseEntity<>("Password reset SuccessFully", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Unable to reset password", HttpStatus.UNAUTHORIZED);
        }
    }
}
