package com.smartwastemanagement.entity;


import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Property {

    @Column(name = "owner_id", nullable = false)
    private Integer ownerId;


}