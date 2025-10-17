package com.smartwastemanagement.controller;

import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentsController {

    private final PaymentService paymentService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> allPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> userPayments(@PathVariable Integer userId) {
        return paymentService.getUserPayments(userId);
    }

}
