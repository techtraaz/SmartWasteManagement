package com.smartwastemanagement.service;

import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.entity.Payment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PaymentService {

    Payment createPayment(Integer userId, Double amount, String method);

    ResponseEntity<ApiResponse> getAllPayments();

    ResponseEntity<ApiResponse> getUserPayments(Integer userId);


}
