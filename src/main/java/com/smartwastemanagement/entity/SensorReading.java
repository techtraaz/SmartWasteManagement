package com.smartwastemanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sensor_reading")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SensorReading {

    @Id
    @Column(name = "reading_id", length = 50)
    private String readingId;

    @Column(name = "bin_id", nullable = false, length = 50)
    private String binId;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "bin_status", nullable = false, length = 50)
    private String binStatus;



}