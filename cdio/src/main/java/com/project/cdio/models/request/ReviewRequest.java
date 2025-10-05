package com.project.cdio.models.request;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReviewRequest {

    private Long roomId;

    private String name;

//    private Long customerId;

    private Long rating;

    private String comment;

}