package com.smartwastemanagement;



import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.entity.Payment;
import com.smartwastemanagement.repository.PaymentRepo;
import com.smartwastemanagement.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepo paymentRepo;

    @InjectMocks
    private PaymentServiceImpl paymentService;


    @Test
    void testCreatePayment() {
        Payment mockSavedPayment = Payment.builder()
                .paymentId(1)
                .userId(10)
                .amount(250.0)
                .paymentMethod("CARD")
                .build();

        when(paymentRepo.save(any(Payment.class))).thenReturn(mockSavedPayment);

        Payment result = paymentService.createPayment(10, 250.0, "CARD");

        assertNotNull(result);
        assertEquals(10, result.getUserId());
        assertEquals(250.0, result.getAmount());
        assertEquals("CARD", result.getPaymentMethod());
        verify(paymentRepo, times(1)).save(any(Payment.class));
    }


    @Test
    void testGetAllPayments_WhenEmpty() {
        when(paymentRepo.findAll()).thenReturn(List.of());

        ResponseEntity<ApiResponse> response = paymentService.getAllPayments();

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("00", response.getBody().getStatusCode());
        assertEquals("no payments found", response.getBody().getMessage());
    }

    @Test
    void testGetAllPayments_WhenDataExists() {
        Payment p = Payment.builder().paymentId(1).amount(200.0).build();
        when(paymentRepo.findAll()).thenReturn(List.of(p));

        ResponseEntity<ApiResponse> response = paymentService.getAllPayments();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("02", response.getBody().getStatusCode());
        assertEquals("All Payments", response.getBody().getMessage());
        assertNotNull(response.getBody().getContent());
    }


    @Test
    void testGetUserPayments_WhenEmpty() {
        when(paymentRepo.findByUserId(5)).thenReturn(List.of());

        ResponseEntity<ApiResponse> response = paymentService.getUserPayments(5);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("00", response.getBody().getStatusCode());
        assertEquals("no payments found", response.getBody().getMessage());
    }

    @Test
    void testGetUserPayments_WhenDataExists() {
        Payment p = Payment.builder().paymentId(2).userId(5).amount(300.0).build();
        when(paymentRepo.findByUserId(5)).thenReturn(List.of(p));

        ResponseEntity<ApiResponse> response = paymentService.getUserPayments(5);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("02", response.getBody().getStatusCode());
        assertEquals("User Payments", response.getBody().getMessage());
        assertNotNull(response.getBody().getContent());
    }
}
