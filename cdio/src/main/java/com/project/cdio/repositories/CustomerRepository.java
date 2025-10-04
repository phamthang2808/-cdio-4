package com.project.cdio.repositories;

import com.project.cdio.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByCustomerId(Long roomId);
    Optional<CustomerEntity> findByEmail(String email);
}
