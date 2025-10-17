package com.smartwastemanagement.repository;

import com.smartwastemanagement.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepo extends JpaRepository<Business,Integer> {

    Business findBusinessByBusinessId(Integer businessId);

}
