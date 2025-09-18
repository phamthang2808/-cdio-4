package com.project.cdio.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReviewDTO {

    private Integer customerId;

    private Integer roomId;

    private Integer rating;

    private String comment;
}
