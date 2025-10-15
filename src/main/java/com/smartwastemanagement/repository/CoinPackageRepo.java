package com.smartwastemanagement.repository;

import com.smartwastemanagement.entity.CoinPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoinPackageRepo extends JpaRepository<CoinPackage,Integer> {

    @Override
    List<CoinPackage> findAll();



    CoinPackage findCoinPackageById(Integer id);


}
