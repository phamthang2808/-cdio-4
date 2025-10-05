package com.project.cdio.services;

import com.project.cdio.entities.ReviewEntity;
import com.project.cdio.exceptions.DataNotFoundException;
import com.project.cdio.models.request.ReviewRequest;
import com.project.cdio.models.responses.AllReviewResponse;
import com.project.cdio.models.responses.AllUserResponse;
import com.project.cdio.models.responses.ReviewResponse;
import org.springframework.data.domain.Page;

public interface IReviewService {

    ReviewEntity createReview(ReviewRequest reviewRequest) throws DataNotFoundException;
    Page<AllReviewResponse> getAllReviews(int page, int limit);
}
