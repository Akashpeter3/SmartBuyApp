package com.app.shopping.service.impl;

import com.app.shopping.constants.AppConstants;
import com.app.shopping.dto.user.User;
import com.app.shopping.repository.AdminRepository;
import com.app.shopping.service.AdminService;
import com.app.shopping.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String addUser(User user) {
        try {
            Optional<User> existingUser = loadUserByUserNameAndEmail(user);

            if (existingUser.isPresent()) {
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
        } catch (Exception e) {

            return "Failed to add user: " + e.getMessage();
        }
    }

    @Override
    public String removeUser(String userName) {
        try {
            Optional<User> userToRemove = loadUserByUserName(userName);
            if (userToRemove.isPresent()) {
                adminRepository.delete(userToRemove.get());
                return AppConstants.USER_DELETED;
            } else {
                return AppConstants.USER_NOT_EXIST;
            }
        } catch (Exception e) {
            return "Failed to remove user: " + e.getMessage();
        }
    }

    @Override
    public User getUserByName(String userName) {
        Optional<User> user = loadUserByUserName(userName);
        return user.orElse(null);
    }

    @Override
    public List<User> getUsers() {
        List<User> users = adminRepository.findAll();
        return users;
    }


    private Optional<User> loadUserByUserName(String userName) {
        try {
            return Optional.ofNullable(adminRepository.findUserByUserName(userName).orElse(null));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Optional<User> loadUserByUserNameAndEmail(User user) {
        try {
            Optional<User> existingUser = adminRepository.findByUsernameAndEmail(user.getUserName(), user.getUserEmail());
            if (existingUser != null) {
                return existingUser;
            }
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

