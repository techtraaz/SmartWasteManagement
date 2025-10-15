package com.smartwastemanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "resident")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resident extends Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resident_id")
    private Integer residentId;

    @Column(name = "household_type", nullable = false, length = 100)
    private String householdType;
}