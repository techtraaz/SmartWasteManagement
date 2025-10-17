package com.smartwastemanagement.service;

import com.smartwastemanagement.dto.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface GarbageService {

    ResponseEntity<ApiResponse> getSensorReading(String binId);

    ResponseEntity<ApiResponse> wasteCollection(String binId , Integer ownerId);
}
