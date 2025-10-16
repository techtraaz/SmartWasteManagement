package com.smartwastemanagement.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FeedbackDto {

    private LocalDate feedbackDate;
    private Integer rating;
    private String comment;


}
