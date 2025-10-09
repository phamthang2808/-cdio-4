package com.project.cdio.repositories;

import com.project.cdio.entities.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findAllByRoom_RoomId(Long id);
}
