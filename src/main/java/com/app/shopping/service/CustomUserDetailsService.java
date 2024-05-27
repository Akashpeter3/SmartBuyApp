package com.app.shopping.service;

import com.app.shopping.dto.admin.AdminDTO;
import com.app.shopping.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminDTO user = adminRepository.findByUsername(username);
        return  new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),new ArrayList<>());

    }
}
