package com.smartwastemanagement;



import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.entity.Account;
import com.smartwastemanagement.entity.Payment;
import com.smartwastemanagement.entity.Refund;
import com.smartwastemanagement.repository.AccountRepo;
import com.smartwastemanagement.repository.PaymentRepo;
import com.smartwastemanagement.repository.RefundRepo;
import com.smartwastemanagement.service.impl.RefundServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RefundServiceTest {

    @Mock
    private RefundRepo refundRepo;

    @Mock
    private PaymentRepo paymentRepo;

    @Mock
    private AccountRepo accountRepo;

    @InjectMocks
    private RefundServiceImpl refundService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testProcessRefund_PaymentNotFound() {

        Integer paymentId = 101;
        String reason = "Duplicate payment";

        when(paymentRepo.findByPaymentId(paymentId)).thenReturn(null);


        ResponseEntity<ApiResponse> response = refundService.processRefund(paymentId, reason);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("01", response.getBody().getStatusCode());
        assertEquals("Payment not found", response.getBody().getMessage());
        assertNull(response.getBody().getContent());

        verify(paymentRepo, times(1)).findByPaymentId(paymentId);
        verifyNoInteractions(accountRepo, refundRepo);
    }


    @Test
    void testProcessRefund_Success() {

        Integer paymentId = 200;
        String reason = "Service not provided";

        Payment payment = Payment.builder()
                .paymentId(paymentId)
                .userId(10)
                .amount(500.0)
                .paymentDate(LocalDateTime.now())
                .paymentMethod("CARD")
                .build();

        Account account = Account.builder()
                .accountId(10)
                .userId(10)
                .balance(1000.0)
                .status("ACT")
                .build();

        Refund refund = Refund.builder()
                .refundId(1)
                .paymentId(paymentId)
                .amount(payment.getAmount())
                .approvedAt(LocalDateTime.now())
                .reason(reason)
                .status("APPROVED")
                .build();

        when(paymentRepo.findByPaymentId(paymentId)).thenReturn(payment);
        when(accountRepo.findByAccountId(payment.getUserId())).thenReturn(account);
        when(refundRepo.save(any(Refund.class))).thenReturn(refund);


        ResponseEntity<ApiResponse> response = refundService.processRefund(paymentId, reason);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("00", response.getBody().getStatusCode());
        assertEquals("Refund processed successfully", response.getBody().getMessage());
        assertTrue(response.getBody().getContent() instanceof Refund);


        verify(accountRepo, times(1)).save(account);
        assertEquals(1500.0, account.getBalance()); // 1000 + 500

        verify(paymentRepo, times(1)).findByPaymentId(paymentId);
        verify(refundRepo, times(1)).save(any(Refund.class));
    }
}
