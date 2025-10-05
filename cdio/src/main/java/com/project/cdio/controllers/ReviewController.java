package com.project.cdio.controllers;

import com.project.cdio.entities.ReviewEntity;
import com.project.cdio.exceptions.DataNotFoundException;
import com.project.cdio.models.request.ReviewRequest;
import com.project.cdio.models.responses.AllReviewResponse;
import com.project.cdio.models.responses.AllUserResponse;
import com.project.cdio.models.responses.ReviewResponse;
import com.project.cdio.services.impl.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{id}")
    public ResponseEntity<ReviewResponse> createReview(
            @PathVariable Long id,
            @RequestBody ReviewRequest reviewRequest
    ) throws DataNotFoundException {
        ReviewResponse reviewResponse = new ReviewResponse();
        reviewRequest.setRoomId(id);
        ReviewEntity reviewEntity = reviewService.createReview(reviewRequest);
        reviewResponse.setMessage("post review successfully");
        return ResponseEntity.ok(reviewResponse);
    }



}
