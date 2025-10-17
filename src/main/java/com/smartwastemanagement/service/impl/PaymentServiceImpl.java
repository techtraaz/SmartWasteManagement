package com.smartwastemanagement.service.impl;

import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.entity.Payment;
import com.smartwastemanagement.repository.PaymentRepo;
import com.smartwastemanagement.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo paymentRepo;


    @Override
    public Payment createPayment(Integer userId, Double amount, String method) {
        Payment payment = new Payment();
        payment.setUserId(userId);
        payment.setAmount(amount);
        payment.setPaymentMethod(method);
        payment.setPaymentDate(LocalDateTime.now());
        return paymentRepo.save(payment);
    }

    @Override
    public ResponseEntity<ApiResponse>  getAllPayments(){

        List<Payment> payments = paymentRepo.findAll();

        if(payments.isEmpty()){
            ApiResponse apiResponse = new ApiResponse("00","no payments found",null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }

        ApiResponse apiResponse = new ApiResponse("02","All Payments",payments);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


    @Override
    public ResponseEntity<ApiResponse> getUserPayments(Integer userId){
        List<Payment> payments = paymentRepo.findByUserId(userId);
        if(payments.isEmpty()){
            ApiResponse apiResponse = new ApiResponse("00","no payments found",null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
        ApiResponse apiResponse = new ApiResponse("02","User Payments",payments);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }









}
