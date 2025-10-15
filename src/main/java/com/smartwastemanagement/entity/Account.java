package com.smartwastemanagement.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "balance", nullable = false)
    private Double balance = 0.0;

    @Column(name = "status", nullable = false, length = 4)
    private String status = "ACT";



}