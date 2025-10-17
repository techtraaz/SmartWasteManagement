package com.smartwastemanagement.service;

import com.smartwastemanagement.dto.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface RefundService {
    ResponseEntity<ApiResponse> processRefund(Integer paymentId, String reason);
}
