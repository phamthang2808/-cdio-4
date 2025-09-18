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
public class DetailBookingEntity extends BaseEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Long detailId;

    @ManyToOne @JoinColumn(name = "booking_id", nullable = false)
    private BookingEntity booking;

    private String serviceName;
    private String serviceDesc;
    private java.math.BigDecimal amount;
    private java.time.LocalDate newCheckout;
}
