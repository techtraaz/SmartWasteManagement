package com.smartwastemanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_coins")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCoins {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_coins_id")
    private Integer userCoinsId;

    @Column(name = "user_id", nullable = false, unique = true)
    private Integer userId;

    @Column(name = "coins", nullable = false)
    private Integer coins = 0;


}