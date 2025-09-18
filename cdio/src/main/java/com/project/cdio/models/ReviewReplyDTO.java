package com.project.cdio.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReviewReplyDTO {

    private Integer reviewId;

    private Integer staffId;

//    private Integer parentId;

    private String content;

    private String visibility;

    private String status;
}
