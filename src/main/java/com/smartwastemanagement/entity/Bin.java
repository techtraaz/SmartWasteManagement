package com.smartwastemanagement.entity;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "bin")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bin {

    @Id
    @Column(name = "bin_id", length = 50)
    private String binId;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "capacity_l", nullable = false)
    private Integer capacityL;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "property_id", nullable = false)
    private Integer propertyId;

    @Column(name = "property_type", nullable = false, length = 20)
    private String propertyType;


}