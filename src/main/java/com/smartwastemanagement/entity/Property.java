package com.smartwastemanagement.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Property {

    @Column(name = "owner_id", nullable = false)
    private Integer ownerId;


}