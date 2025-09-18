package com.project.cdio.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RoomDTO {

    private String name;

    private String image;

    private String address;

    private String description;
}
