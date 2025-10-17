package com.smartwastemanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dispute")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dispute {

    @Id
    @Column(name = "dispute_id")
    private String disputeId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "payment_id")
    private Integer paymentId;

    @Column(name = "reason")
    private String reason;

    @Column(name = "open_at")
    private LocalDateTime openAt;

    @Column(name = "status")
    private String status;
}
