package com.project.cdio.services.impl;

import com.project.cdio.components.LocalizationUtils;
import com.project.cdio.entities.CustomerEntity;
import com.project.cdio.entities.ReviewEntity;
import com.project.cdio.entities.RoomEntity;
import com.project.cdio.entities.UserEntity;
import com.project.cdio.exceptions.DataNotFoundException;
import com.project.cdio.models.dto.ReviewDTO;
import com.project.cdio.models.request.ReviewRequest;
import com.project.cdio.models.responses.AllReviewResponse;
import com.project.cdio.models.responses.AllUserResponse;
import com.project.cdio.models.responses.ReviewResponse;
import com.project.cdio.repositories.CustomerRepository;
import com.project.cdio.repositories.ReviewRepository;
import com.project.cdio.repositories.RoomRepository;
import com.project.cdio.services.IReviewService;
import com.project.cdio.utils.MessageKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {

    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;
    private final LocalizationUtils localizationUtils;

    public ReviewEntity createReview(ReviewRequest reviewRequest) throws DataNotFoundException {
        CustomerEntity customer = new CustomerEntity();
        customer.setName(reviewRequest.getName());
        customerRepository.save(customer);

        Optional<RoomEntity> optionnalRoom = roomRepository.findByRoomId(reviewRequest.getRoomId());
        if(optionnalRoom.isEmpty()){
            throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.FIND_ROOM_FAILED));
        }
        RoomEntity existingRoom = optionnalRoom.get();

        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setRoom(existingRoom);
        reviewEntity.setCustomer(customer);
        reviewEntity.setRating(reviewRequest.getRating());
        reviewEntity.setComment(reviewRequest.getComment());
        return reviewRepository.save(reviewEntity);
    }

    @Override
    public Page<AllReviewResponse> getAllReviews(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<ReviewEntity> data = reviewRepository.findAll(pageable);
        return data.map(AllReviewResponse::new);
    }

    @Override
    public List<ReviewDTO> getReviewById(Long id) throws DataNotFoundException {
        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        Optional<List<RoomEntity>> optionalRoom = roomRepository.findAllByRoomId(id);
        if(optionalRoom.isEmpty() || optionalRoom.get().isEmpty()){
            throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.FIND_ROOM_FAILED));
        }
        List<RoomEntity> existingRooms = optionalRoom.get();
        for(RoomEntity roomEntities:  existingRooms){
            List<ReviewEntity> existingReviews = reviewRepository.findAllByRoom_RoomId(roomEntities.getRoomId());
            for(ReviewEntity reviewDTO: existingReviews){
                ReviewDTO review = new ReviewDTO(reviewDTO);
                reviewDTOS.add(review);
            }

        }
        return reviewDTOS;
    }
}
