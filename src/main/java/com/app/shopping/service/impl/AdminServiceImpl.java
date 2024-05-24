package com.app.shopping.service.impl;

import com.app.shopping.dto.user.User;
import com.app.shopping.repository.AdminRepository;
import com.app.shopping.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Override
    public String addUser(User user) {

      String userName =  adminRepository.save(user).getUserName();
      return userName;

    }
}
