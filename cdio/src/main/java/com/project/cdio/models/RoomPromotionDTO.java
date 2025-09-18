package com.project.cdio.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RoomPromotionDTO {

    private Integer promoId;

    private Integer priority;

    private Boolean stackable;

    private Boolean active;
}
