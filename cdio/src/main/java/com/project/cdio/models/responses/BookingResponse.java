package com.project.cdio.models.responses;

import com.project.cdio.entities.BookingEntity;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BookingResponse {

    private String message;

    private Long roomId;

    private String name;

    private String email;

    private String phone;

    private LocalDate checkinDate;

    private LocalDate checkoutDate;

    private String status;
}
