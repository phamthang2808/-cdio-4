package com.project.cdio.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RoomAllDTO {
    private Long id;
    private String title;
    private String image;
    private String address;
    private String description;
    private Integer guests;
    private Integer size;
    private String beds;
    private String view;
    private Double price;
    private Double oldPrice;
    private Integer discount;
    private boolean airConditioning;
    private boolean wifi;
    private boolean hairDryer;
    private boolean petsAllowed;
    private boolean nonSmoking;
}
