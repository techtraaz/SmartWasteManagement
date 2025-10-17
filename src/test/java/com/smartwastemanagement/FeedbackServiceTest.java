package com.smartwastemanagement;


import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.dto.FeedbackDto;
import com.smartwastemanagement.entity.Feedback;
import com.smartwastemanagement.repository.FeedbackRepo;
import com.smartwastemanagement.service.impl.FeedbackServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeedbackServiceTest {

    @Mock
    private FeedbackRepo feedbackRepo;

    @InjectMocks
    private FeedbackServiceImpl feedbackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testPublishFeedback_Success() {

        FeedbackDto dto = new FeedbackDto();
        dto.setFeedbackDate(LocalDate.of(2025, 10, 17));
        dto.setComment("Good service");
        dto.setRating(5);

        Feedback mockFeedback = Feedback.builder()
                .feedbackDate(dto.getFeedbackDate())
                .comment(dto.getComment())
                .rating(dto.getRating())
                .createdAt(LocalDateTime.now())
                .isAnonymous(true)
                .build();

        when(feedbackRepo.save(any(Feedback.class))).thenReturn(mockFeedback);


        ResponseEntity<ApiResponse> response = feedbackService.publishFeedback(dto);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("02", response.getBody().getStatusCode());
        assertEquals("feedback successfully published", response.getBody().getMessage());
        assertTrue(response.getBody().getContent() instanceof Feedback);

        verify(feedbackRepo, times(1)).save(any(Feedback.class));
    }


    @Test
    void testPublishFeedback_Failure() {

        FeedbackDto dto = new FeedbackDto();
        dto.setFeedbackDate(LocalDate.now());
        dto.setComment("Test feedback");
        dto.setRating(4);

        when(feedbackRepo.save(any(Feedback.class))).thenThrow(new RuntimeException("Database error"));


        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            feedbackService.publishFeedback(dto);
        });

        assertEquals("Database error", thrown.getMessage());
        verify(feedbackRepo, times(1)).save(any(Feedback.class));
    }
}
