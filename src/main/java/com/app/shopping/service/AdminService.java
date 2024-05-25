package com.app.shopping.service;

import com.app.shopping.dto.user.User;

public interface AdminService {
    String addUser(User user);

    String removeUser(String userName);
}
