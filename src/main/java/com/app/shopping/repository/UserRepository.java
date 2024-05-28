package com.app.shopping.repository;

import com.app.shopping.dto.user.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDTO,Integer> {

    @Query("SELECT u FROM UserDTO u WHERE u.username = :username AND u.email = :email")
    Optional<UserDTO> findByUsernameAndEmail(String username, String email);
    @Query("SELECT u FROM UserDTO u WHERE u.username = :username")
    Optional<UserDTO> findUserByUserName(String username);


}



