package com.app.shopping.repository;

import com.app.shopping.dto.admin.AdminDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminDTO,Integer> {

    AdminDTO findByUsername(String userName);
}
