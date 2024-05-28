package com.app.shopping.service;

import com.app.shopping.dto.user.UserDTO;

import java.util.List;

public interface UserService {
    String registerUser(UserDTO userDTO);

    String removeUser(String userName);

    UserDTO getUserByName(String userName);


    List<UserDTO> getUsers();


    boolean loginUser(String username, String password);

    boolean resetPassword(String username,String oldPassWord,String newPassword);
}
