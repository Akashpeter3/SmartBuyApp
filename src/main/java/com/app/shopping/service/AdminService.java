package com.app.shopping.service;

import com.app.shopping.dto.admin.AdminDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminService  {
    boolean adminLogin(AdminDTO adminDTO);
}
