package com.project.cdio.services.impl;

import com.project.cdio.components.LocalizationUtils;
import com.project.cdio.entities.ReplyEntity;
import com.project.cdio.entities.ReviewEntity;
import com.project.cdio.entities.UserEntity;
import com.project.cdio.exceptions.DataNotFoundException;
import com.project.cdio.models.dto.ReplyDTO;
import com.project.cdio.repositories.ReplyRepository;
import com.project.cdio.repositories.ReviewRepository;
import com.project.cdio.repositories.UserRepository;
import com.project.cdio.services.IReplyService;
import com.project.cdio.utils.MessageKeys;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplyService implements IReplyService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final LocalizationUtils localizationUtils;
    private final ReplyRepository replyRepository;

    @Override
    public ReplyEntity createReply(ReplyDTO replyDTO) throws DataNotFoundException {
        Optional<ReviewEntity> optionalReview = reviewRepository.findById(replyDTO.getReviewId());
        if(optionalReview.isEmpty()){
            throw new DataNotFoundException("not find review");
        }
        ReviewEntity review = optionalReview.get();

        Optional<UserEntity> optionalUser = userRepository.findByUserId(replyDTO.getStaffId());
        if(optionalUser.isEmpty()){
            throw new DataNotFoundException("not find staff");
        }
        UserEntity staff = optionalUser.get();
        ReplyEntity replyEntity = new ReplyEntity();
        replyEntity.setContent(replyDTO.getContent());
        replyEntity.setReview(review);
        replyEntity.setStaff(staff);
        return replyRepository.save(replyEntity);
    }
}
