package com.app.shopping.repository;

import com.app.shopping.dto.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<User,Long> {

    @Query("SELECT u FROM User u WHERE u.userName = :userName AND u.userEmail = :userEmail")
    User findByUsernameAndEmail(String userName, String userEmail);
}
