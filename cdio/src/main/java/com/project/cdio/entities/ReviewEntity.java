package com.project.cdio.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewEntity extends BaseEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @ManyToOne @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ManyToOne @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

    private Integer rating;
    
    private String comment;
}
