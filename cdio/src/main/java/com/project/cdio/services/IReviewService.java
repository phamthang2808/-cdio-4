package com.project.cdio.services;

import com.project.cdio.entities.ReviewEntity;
import com.project.cdio.exceptions.DataNotFoundException;
import com.project.cdio.models.dto.ReviewDTO;
import com.project.cdio.models.request.ReviewRequest;
import com.project.cdio.models.responses.AllReviewResponse;
import com.project.cdio.models.responses.AllUserResponse;
import com.project.cdio.models.responses.ReviewResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IReviewService {

    ReviewEntity createReview(ReviewRequest reviewRequest) throws DataNotFoundException;
    Page<AllReviewResponse> getAllReviews(int page, int limit);
    List<ReviewDTO> getReviewById(Long id) throws DataNotFoundException;
}
