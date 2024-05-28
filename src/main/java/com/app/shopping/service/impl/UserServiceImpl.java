package com.app.shopping.service.impl;

import com.app.shopping.constants.AppConstants;
import com.app.shopping.dto.user.UserDTO;
import com.app.shopping.repository.UserRepository;
import com.app.shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public String registerUser(UserDTO userDTO) {
        try {
            Optional<UserDTO> existingUser = loadUserByUserNameAndEmail(userDTO);

            if (existingUser.isPresent()) {
                return AppConstants.USER_EXISTS;
            } else {
                userDTO.setStatusFlag(true);
                if (userDTO.getStatusFlag()) {
                    userDTO.setStatus("Active");
                    userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                }
                String userName = userRepository.save(userDTO).getUsername();
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
                userRepository.delete(userToRemove.get());
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
        List<UserDTO> userDTOS = userRepository.findAll();
        return userDTOS;
    }

    @Override
    public boolean loginUser(String username, String password) {
        Optional<UserDTO> optionalUser = loadUserByUserName(username);
        if (optionalUser.isPresent()) {
            UserDTO user = optionalUser.get();

            // Check if the password matches
            if (passwordEncoder.matches(password, user.getPassword())) {
                // Password matches
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean resetPassword(String username, String oldPassWord,String newPassword) {
        Optional<UserDTO> userDetails = userRepository.findUserByUserName(username);
        String encryptedPassword = userDetails.get().getPassword();
        boolean isMatching = passwordEncoder.matches(oldPassWord, encryptedPassword);
        if (isMatching) {
           userDetails.get().setPassword(passwordEncoder.encode(newPassword));
           userRepository.save(userDetails.get());
           return true;
        } else {
            return false;
        }
    }


    private Optional<UserDTO> loadUserByUserName(String userName) {
        try {
            return Optional.ofNullable(userRepository.findUserByUserName(userName).orElse(null));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Optional<UserDTO> loadUserByUserNameAndEmail(UserDTO userDTO) {
        try {
            Optional<UserDTO> existingUser = userRepository.findByUsernameAndEmail(userDTO.getUsername(), userDTO.getEmail());
            if (existingUser != null) {
                return existingUser;
            }
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

