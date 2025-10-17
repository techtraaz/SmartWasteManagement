package com.smartwastemanagement.service;

import com.smartwastemanagement.dto.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface DisputeService {

    ResponseEntity<ApiResponse> createDispute(Integer userId, Integer paymentId, String reason);

    ResponseEntity<ApiResponse> resolveDispute(String disputeId);

    ResponseEntity<ApiResponse> getAllDisputes();

}
