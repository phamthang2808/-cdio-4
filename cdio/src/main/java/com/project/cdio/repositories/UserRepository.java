package com.project.cdio.repositories;

import com.project.cdio.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.cdio.models.*;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
    //SELECT * FROM users WHERE phoneNumber=?
}

