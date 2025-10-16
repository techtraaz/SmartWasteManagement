package com.smartwastemanagement.repository;

import com.smartwastemanagement.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResidentRepo extends JpaRepository<Resident,Integer> {

    Resident findResidentByResidentId(Integer residentId);

}
