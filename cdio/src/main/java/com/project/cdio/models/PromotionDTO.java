package com.project.cdio.models;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PromotionDTO {

    private String promoName;

    private String description;

    private BigDecimal discountPct;

    private BigDecimal discountAmt;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status;
}
