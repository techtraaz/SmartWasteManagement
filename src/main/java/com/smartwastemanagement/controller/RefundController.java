package com.smartwastemanagement.controller;

import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.dto.RefundRequestDto;
import com.smartwastemanagement.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/refund")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    @PostMapping("/process")
    public ResponseEntity<ApiResponse> refundPayment(@RequestBody RefundRequestDto refundReq) {
        return refundService.processRefund(refundReq.getPaymentId(), refundReq.getReason());
    }



}
