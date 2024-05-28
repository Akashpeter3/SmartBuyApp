package com.app.shopping.service.impl;

import com.app.shopping.dto.admin.AdminDTO;
import com.app.shopping.repository.AdminRepository;
import com.app.shopping.service.AdminService;
import com.app.shopping.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AuthenticationManager authenticationManager;


    Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    @Override
    public boolean adminLogin(AdminDTO adminDTO) {
        adminDTO.setPassword(PasswordUtil.generateRandomPassword());
        adminRepository.save(adminDTO);
        logger.info("Admin details saved successfully!");

     Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(adminDTO.getUsername(), adminDTO.getPassword()));

     if (authentication.isAuthenticated()) {
         logger.info("Admin Authenticated successfully!");
         return  true;
     }else {
         logger.info("Unable to authenticate admin");
         return false;
     }

    }
}
