package com.project.cdio.models.dto;

import com.project.cdio.entities.ReviewEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Data
public class ReviewDTO {


    private Long roomId;

    private Long rating;

    private String comment;

    private String name;

    public ReviewDTO(ReviewEntity r){
        this.roomId = r.getRoom().getRoomId();
        this.rating = r.getRating();
        this.comment = r.getComment();
        this.name = r.getCustomer().getName();
    }

}
