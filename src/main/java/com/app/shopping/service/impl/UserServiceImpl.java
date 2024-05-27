package com.app.shopping.service.impl;

import com.app.shopping.constants.AppConstants;
import com.app.shopping.dto.user.UserDTO;
import com.app.shopping.repository.UserRepository;
import com.app.shopping.service.UserService;
import com.app.shopping.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository adminRepository;



    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public String addUser(UserDTO userDTO) {
        try {
            Optional<UserDTO> existingUser = loadUserByUserNameAndEmail(userDTO);

            if (existingUser.isPresent()) {
                return AppConstants.USER_EXISTS;
            } else {
                userDTO.setStatusFlag(true);
                if (userDTO.getStatusFlag()) {
                    userDTO.setStatus("Active");
                    userDTO.setPassword(PasswordUtil.generateRandomPassword());
                }
                String userName = adminRepository.save(userDTO).getUsername();
                return userName;
            }
        } catch (Exception e) {

            return "Failed to add user: " + e.getMessage();
        }
    }

    @Override
    public String removeUser(String userName) {
        try {
            Optional<UserDTO> userToRemove = loadUserByUserName(userName);
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
    public UserDTO getUserByName(String userName) {
        Optional<UserDTO> user = loadUserByUserName(userName);
        return user.orElse(null);
    }

    @Override
    public List<UserDTO> getUsers() {
        List<UserDTO> userDTOS = adminRepository.findAll();
        return userDTOS;
    }



    private Optional<UserDTO> loadUserByUserName(String userName) {
        try {
            return Optional.ofNullable(adminRepository.findUserByUserName(userName).orElse(null));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Optional<UserDTO> loadUserByUserNameAndEmail(UserDTO userDTO) {
        try {
            Optional<UserDTO> existingUser = adminRepository.findByUsernameAndEmail(userDTO.getUsername(), userDTO.getEmail());
            if (existingUser != null) {
                return existingUser;
            }
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

