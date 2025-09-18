package com.project.cdio.models;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class InvoiceItemDTO {

    private Integer invoiceId;

    private String itemType;

    private String description;

    private BigDecimal qty;

    private BigDecimal unitPrice;

    private BigDecimal taxRate;

    private BigDecimal lineAmount;

    private BigDecimal lineDiscount;
}
