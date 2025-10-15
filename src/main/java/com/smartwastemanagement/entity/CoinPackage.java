package com.smartwastemanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "coin_package")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoinPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_id")
    private Integer Id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "coins")
    private Integer coins;


}