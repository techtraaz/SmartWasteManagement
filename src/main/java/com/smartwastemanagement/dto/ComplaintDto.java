package com.smartwastemanagement.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplaintDto{

    private String complaintId;
    private Integer userId;
    private String category;
    private String description;
    private String status;
    private String evidenceUrl;
    private LocalDateTime createdAt;
    private String preferredContact;
}
