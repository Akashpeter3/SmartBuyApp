package com.app.shopping.service.impl;

import com.app.shopping.constants.AppConstants;
import com.app.shopping.dto.user.User;
import com.app.shopping.repository.AdminRepository;
import com.app.shopping.service.AdminService;
import com.app.shopping.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public String addUser(User user) {
        Optional<User> existingUser = loadUserByUserNameAndEmail(user);

        if (existingUser.isPresent() ) {
            return AppConstants.USER_EXISTS;
        } else {
            user.setStatusFlag(true);
            if (user.getStatusFlag()) {
                user.setUserStatus("Active");
                user.setUserPassword(PasswordUtil.generateRandomPassword());
            }
            String userName = adminRepository.save(user).getUserName();
            return userName;
        }
    }

    private Optional<User> loadUserByUserNameAndEmail(User user) {
      Optional<User> existingUser = adminRepository.findByUsernameAndEmail(user.getUserName(),user.getUserEmail());
        if (existingUser != null) {
            return existingUser;
        }
        return null;
    }
}
