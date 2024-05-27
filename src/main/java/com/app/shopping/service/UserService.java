package com.app.shopping.service;

import com.app.shopping.dto.user.UserDTO;

import java.util.List;

public interface UserService {
    String addUser(UserDTO userDTO);

    String removeUser(String userName);

    UserDTO getUserByName(String userName);


    List<UserDTO> getUsers();


}
