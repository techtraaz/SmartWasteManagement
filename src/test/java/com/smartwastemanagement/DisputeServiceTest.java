package com.smartwastemanagement;


import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.entity.Dispute;
import com.smartwastemanagement.repository.DisputeRepo;
import com.smartwastemanagement.service.impl.DisputeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DisputeServiceTest {

    @Mock
    private DisputeRepo disputeRepo;

    @InjectMocks
    private DisputeServiceImpl disputeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testCreateDispute_Success() {

        Integer userId = 1;
        Integer paymentId = 1001;
        String reason = "Incorrect amount charged";

        Dispute mockDispute = Dispute.builder()
                .disputeId(UUID.randomUUID().toString())
                .userId(userId)
                .paymentId(paymentId)
                .reason(reason)
                .openAt(LocalDateTime.now())
                .status("PENDING")
                .build();

        when(disputeRepo.save(any(Dispute.class))).thenReturn(mockDispute);


        ResponseEntity<ApiResponse> response = disputeService.createDispute(userId, paymentId, reason);


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("00", response.getBody().getStatusCode());
        assertEquals("Dispute created successfully", response.getBody().getMessage());

        verify(disputeRepo, times(1)).save(any(Dispute.class));
    }


    @Test
    void testResolveDispute_Success() {
        // Arrange
        String disputeId = "abc123";
        Dispute dispute = new Dispute(disputeId, 1, 200, "Test", LocalDateTime.now(), "PENDING");

        when(disputeRepo.findById(disputeId)).thenReturn(Optional.of(dispute));
        when(disputeRepo.save(any(Dispute.class))).thenReturn(dispute);


        ResponseEntity<ApiResponse> response = disputeService.resolveDispute(disputeId);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("00", response.getBody().getStatusCode());
        assertEquals("RESOLVED", dispute.getStatus());
        verify(disputeRepo, times(1)).save(dispute);
    }


    @Test
    void testResolveDispute_NotFound() {

        String disputeId = "missing";
        when(disputeRepo.findById(disputeId)).thenReturn(Optional.empty());


        ResponseEntity<ApiResponse> response = disputeService.resolveDispute(disputeId);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("01", response.getBody().getStatusCode());
        verify(disputeRepo, never()).save(any(Dispute.class));
    }


    @Test
    void testGetAllDisputes_Found() {
        // Arrange
        List<Dispute> disputes = List.of(
                new Dispute("1", 1, 101, "reason1", LocalDateTime.now(), "PENDING"),
                new Dispute("2", 2, 102, "reason2", LocalDateTime.now(), "RESOLVED")
        );

        when(disputeRepo.findAll()).thenReturn(disputes);


        ResponseEntity<ApiResponse> response = disputeService.getAllDisputes();


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("00", response.getBody().getStatusCode());
        assertTrue(((List<?>) response.getBody().getContent()).size() == 2);
        verify(disputeRepo, times(1)).findAll();
    }


    @Test
    void testGetAllDisputes_Empty() {

        when(disputeRepo.findAll()).thenReturn(Collections.emptyList());


        ResponseEntity<ApiResponse> response = disputeService.getAllDisputes();


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("01", response.getBody().getStatusCode());
        verify(disputeRepo, times(1)).findAll();
    }
}
