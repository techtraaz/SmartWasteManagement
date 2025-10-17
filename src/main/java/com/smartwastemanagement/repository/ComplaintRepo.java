package com.smartwastemanagement.repository;

import com.smartwastemanagement.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepo extends JpaRepository<Complaint, String> {

    List<Complaint> findByUserId(Integer userId);

}
