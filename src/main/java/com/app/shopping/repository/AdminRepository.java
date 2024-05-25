package com.app.shopping.repository;

import com.app.shopping.dto.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<User,Long> {

    @Query("SELECT u FROM User u WHERE u.userName = :userName AND u.userEmail = :userEmail")
    Optional<User> findByUsernameAndEmail(String userName, String userEmail);
}
