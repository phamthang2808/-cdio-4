package com.project.cdio.models;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class InvoiceDTO {

    private Integer customerId;

    private Integer staffId;

    private Integer detailId;

    private LocalDateTime issuedAt;

    private String status;

    private BigDecimal subtotal;

    private BigDecimal discount;

    private BigDecimal taxAmount;

    private BigDecimal totalAmount;

    private String note;
}
