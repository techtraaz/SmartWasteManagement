package com.smartwastemanagement.repository;

import com.smartwastemanagement.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundRepo extends JpaRepository<Refund, Integer> {
}
