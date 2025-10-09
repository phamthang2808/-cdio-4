package com.project.cdio.models.responses;

import com.project.cdio.entities.ReviewEntity;
import com.project.cdio.entities.ReplyEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Comparator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AllReviewResponse {

    private String name;

    private Long rating;

    private String comment;

    private String content;

    private LocalDateTime createdAt;

    public AllReviewResponse(ReviewEntity r){
        this.name = r.getCustomer().getName();
        this.rating = r.getRating();
         this.comment = r.getComment();
         this.createdAt = r.getCreatedAt();
         this.content = r.getReviewReply().stream()
                 .sorted(Comparator.comparing(ReplyEntity::getCreatedAt)) // tăng dần
                 .reduce((a, b) -> b) // lay cuoi cung
                 .map(ReplyEntity::getContent)
                 .orElse("(Chưa phản hồi)");
    }
}
