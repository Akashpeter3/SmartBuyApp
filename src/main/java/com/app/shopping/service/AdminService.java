package com.app.shopping.service;

import com.app.shopping.dto.user.User;

import java.util.List;

public interface AdminService {
    String addUser(User user);

    String removeUser(String userName);

    User getUserByName(String userName);


    List<User> getUsers();
}
