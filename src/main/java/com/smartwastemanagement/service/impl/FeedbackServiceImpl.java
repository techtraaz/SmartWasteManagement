package com.smartwastemanagement.service.impl;

import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.dto.FeedbackDto;
import com.smartwastemanagement.entity.Feedback;
import com.smartwastemanagement.repository.FeedbackRepo;
import com.smartwastemanagement.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepo feedbackRepo;

    @Override
    public ResponseEntity<ApiResponse> publishFeedback(FeedbackDto feedbackDto){

        Feedback feedback = Feedback.builder()
                .feedbackDate(feedbackDto.getFeedbackDate())
                .comment(feedbackDto.getComment())
                .rating(feedbackDto.getRating())
                .createdAt(LocalDateTime.now())
                .isAnonymous(true)
                .build();
        feedbackRepo.save(feedback);

        ApiResponse apiResponse = new ApiResponse("02","feedback successfully published",feedback);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }

}
