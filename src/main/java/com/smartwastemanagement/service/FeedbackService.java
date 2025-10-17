package com.smartwastemanagement.service;

import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.dto.FeedbackDto;
import org.springframework.http.ResponseEntity;

public interface FeedbackService {

    ResponseEntity<ApiResponse> publishFeedback(FeedbackDto feedbackDto);

}
