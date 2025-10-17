package com.smartwastemanagement.dto;

import lombok.Data;
import java.time.LocalDate;


@Data
public class FeedbackDto {

    private LocalDate feedbackDate;
    private Integer rating;
    private String comment;


}
