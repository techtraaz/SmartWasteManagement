package com.smartwastemanagement.repository;

import com.smartwastemanagement.entity.Bin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BinRepo extends JpaRepository<Bin,String> {

    Bin findBinByBinId(String id);

}
