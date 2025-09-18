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
public class PromotionEntity extends BaseEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promo_id")
    private Long promoId;

    private String promoName;
    private String description;
    private java.math.BigDecimal discountPct;
    private java.math.BigDecimal discountAmt;
    private java.time.LocalDate startDate;
    private java.time.LocalDate endDate;
    private String status;
}
