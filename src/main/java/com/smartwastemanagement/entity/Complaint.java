package com.smartwastemanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "complaint")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Complaint {

    @Id
    @Column(name = "complaint_id")
    private String complaintId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "evidence_url")
    private String evidenceUrl;

    @Column(name = "created_at", columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    @Column(name = "preferred_contact")
    private String preferredContact;


}
