package com.project.cdio.models;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RoomTypePriceDTO {

    private Integer typeId;

    private BigDecimal price;

    private String currency;          // "VND", ...

    private LocalDate startDate;

    private LocalDate endDate;

}
