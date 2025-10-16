package com.smartwastemanagement.controller;


import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.dto.FeedbackDto;
import com.smartwastemanagement.entity.Feedback;
import com.smartwastemanagement.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping("/publish")
    public ResponseEntity<ApiResponse> publishFeedback(@RequestBody FeedbackDto feedbackDto){
        return feedbackService.publishFeedback(feedbackDto);

    }

}
