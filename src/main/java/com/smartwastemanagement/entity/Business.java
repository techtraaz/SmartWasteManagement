package com.smartwastemanagement.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "business")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Business extends Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_id")
    private Integer businessId;

    @Column(name = "business_type", nullable = false, length = 100)
    private String businessType;
}