package com.project.cdio.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewReplyEntity extends BaseEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long replyId;

    @ManyToOne @JoinColumn(name = "review_id", nullable = false)
    private ReviewEntity review;

    @ManyToOne @JoinColumn(name = "staff_id", nullable = false)
    private UserEntity staff;

    private Integer parentId; // bạn có thể làm @ManyToOne self-reference

    private String content;
    private String visibility;
    private String status;
}
