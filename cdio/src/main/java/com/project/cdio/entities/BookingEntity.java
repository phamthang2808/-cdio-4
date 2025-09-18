package com.project.cdio.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingEntity extends BaseEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @ManyToOne @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ManyToOne @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

    private java.time.LocalDate checkinDate;
    private java.time.LocalDate checkoutDate;
    private String status;
}
