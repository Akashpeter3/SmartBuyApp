package com.app.shopping.service.impl;

import com.app.shopping.constants.AppConstants;
import com.app.shopping.dto.user.User;
import com.app.shopping.repository.AdminRepository;
import com.app.shopping.service.AdminService;
import com.app.shopping.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public String addUser(User user) {
        String existingUserName = loadUserByUserNameAndEmail(user);
        if (!existingUserName.equalsIgnoreCase(user.getUserName())) {
        user.setStatusFlag(true);
        if (user.getStatusFlag()) {
            user.setUserStatus("Active");
            user.setUserPassword(PasswordUtil.generateRandomPassword());
        }
        String userName = adminRepository.save(user).getUserName();
        return userName;
    }else {
            return AppConstants.USER_EXISTS;
        }
    }

    private String loadUserByUserNameAndEmail(User user) {
      User existingUser =   adminRepository.findByUsernameAndEmail(user.getUserName(),user.getUserEmail());
        if (existingUser != null) {
            return existingUser.getUserName();
        }
        return null;
    }
}
