package com.project.cdio.repositories;

import com.project.cdio.entities.ManagementCustomerEntity;
import com.project.cdio.entities.ManagementRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagementCustomerRepository extends JpaRepository<ManagementCustomerEntity, Long> {

    // "SELECT * FROM management_rooms WHERE staff.userId = :staffId"
    List<ManagementCustomerEntity> findByStaff_UserId(Long staffId); // tim kiem boi staff trong class UserEntity , gach duoi la lui vao truong cua userentity
}
