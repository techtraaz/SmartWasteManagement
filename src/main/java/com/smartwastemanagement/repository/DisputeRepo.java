package com.smartwastemanagement.repository;

import com.smartwastemanagement.entity.Dispute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisputeRepo extends JpaRepository<Dispute, String> {

    List<Dispute> findByUserId(Integer userId);

    List<Dispute> findByStatus(String status);
}
