package com.smartwastemanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "waste_collection")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WasteCollection {

    @Id
    @Column(name = "collection_id", length = 50)
    private String collectionId;

    @Column(name = "bin_id", nullable = false, length = 50)
    private String binId;

    @Column(name = "collection_date", nullable = false)
    private LocalDate collectionDate;

    @Column(name = "waste_type", nullable = false, length = 100)
    private String wasteType;

    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @Column(name = "owner_id", nullable = false)
    private Integer ownerId;




}