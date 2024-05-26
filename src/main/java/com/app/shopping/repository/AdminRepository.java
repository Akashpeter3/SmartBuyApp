package com.app.shopping.repository;

import com.app.shopping.dto.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<User,Long> {

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.email = :email")
    Optional<User> findByUsernameAndEmail(String username, String email);
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findUserByUserName(String username);
}
