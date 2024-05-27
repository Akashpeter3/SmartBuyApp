package com.app.shopping.service.impl;

import com.app.shopping.dto.admin.AdminDTO;
import com.app.shopping.repository.AdminRepository;
import com.app.shopping.service.AdminService;
import com.app.shopping.util.PasswordUtil;
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

    @Override
    public boolean adminLogin(AdminDTO adminDTO) {
        adminDTO.setPassword(PasswordUtil.generateRandomPassword());
        adminRepository.save(adminDTO);

     Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(adminDTO.getUsername(), adminDTO.getPassword()));

     if (authentication.isAuthenticated()) {
         return  true;
     }else {
         return false;
     }

    }
}
