package com.project.cdio.repositories;

import com.project.cdio.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository  extends JpaRepository<BookingEntity, Long> {
//    BookingEntity findBy
}
