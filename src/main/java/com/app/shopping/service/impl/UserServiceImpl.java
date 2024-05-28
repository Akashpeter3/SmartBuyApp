package com.app.shopping.service.impl;

import com.app.shopping.constants.AppConstants;
import com.app.shopping.dto.user.UserDTO;
import com.app.shopping.repository.UserRepository;
import com.app.shopping.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
            logger.error("Failed to register user: {}", userDTO.getUsername(), e);
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
            logger.error("Failed to remove user: {}", userName, e);
            return "Failed to remove user: " + e.getMessage();
        }
    }

    @Override
    public UserDTO getUserByName(String userName) {
        try {
            Optional<UserDTO> user = loadUserByUserName(userName);
            return user.orElse(null);
        } catch (Exception e) {
            logger.error("Failed to get user by name: {}", userName, e);
            return null;
        }
    }

    @Override
    public List<UserDTO> getUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            logger.error("Failed to get users", e);
            return null;
        }
    }

    @Override
    public boolean loginUser(String username, String password) {
        try {
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
        } catch (Exception e) {
            logger.error("Failed to login user: {}", username, e);
            return false;
        }
    }

    @Override
    public boolean resetPassword(String username, String oldPassword, String newPassword) {
        try {
            Optional<UserDTO> userDetails = userRepository.findUserByUserName(username);
            if (userDetails.isPresent()) {
                String encryptedPassword = userDetails.get().getPassword();
                boolean isMatching = passwordEncoder.matches(oldPassword, encryptedPassword);
                if (isMatching) {
                    userDetails.get().setPassword(passwordEncoder.encode(newPassword));
                    userRepository.save(userDetails.get());
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("Failed to reset password for user: {}", username, e);
            return false;
        }
    }

    private Optional<UserDTO> loadUserByUserName(String userName) {
        try {
            return Optional.ofNullable(userRepository.findUserByUserName(userName).orElse(null));
        } catch (Exception e) {
            logger.error("Failed to load user by username: {}", userName, e);
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
            logger.error("Failed to load user by username and email: {} and {}", userDTO.getUsername(), userDTO.getEmail(), e);
            return Optional.empty();
        }
    }
}
