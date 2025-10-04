package com.project.cdio.models.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RoomTypeDTO {
    private String typeName;

    private String description;

    private Integer roomId;

}
