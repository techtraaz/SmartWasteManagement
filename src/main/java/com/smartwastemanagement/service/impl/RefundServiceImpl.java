package com.smartwastemanagement.service.impl;

import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.entity.Account;
import com.smartwastemanagement.entity.Payment;
import com.smartwastemanagement.entity.Refund;
import com.smartwastemanagement.repository.AccountRepo;
import com.smartwastemanagement.repository.PaymentRepo;
import com.smartwastemanagement.repository.RefundRepo;
import com.smartwastemanagement.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

    private final RefundRepo refundRepo;
    private final PaymentRepo paymentRepo;
    private final AccountRepo accountRepo;

    @Override
    public ResponseEntity<ApiResponse> processRefund(Integer paymentId, String reason) {

        Payment payment = paymentRepo.findByPaymentId(paymentId);
        if (payment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("01", "Payment not found", null));
        }

        Account account = accountRepo.findByAccountId(payment.getUserId());
        account.setBalance(account.getBalance() + payment.getAmount());
        accountRepo.save(account);

        Refund refund = Refund.builder()
                .paymentId(paymentId)
                .amount(payment.getAmount())
                .approvedAt(LocalDateTime.now())
                .reason(reason)
                .status("APPROVED")
                .build();

        Refund savedRefund = refundRepo.save(refund);

        ApiResponse response = new ApiResponse("00", "Refund processed successfully", savedRefund);
        return ResponseEntity.ok(response);
    }
}
