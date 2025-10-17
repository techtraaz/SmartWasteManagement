package com.smartwastemanagement;


import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.dto.ComplaintDto;
import com.smartwastemanagement.entity.Complaint;
import com.smartwastemanagement.repository.ComplaintRepo;
import com.smartwastemanagement.service.impl.ComplaintServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComplaintServiceTest {

    @Mock
    private ComplaintRepo complaintRepo;

    @InjectMocks
    private ComplaintServiceImpl complaintService;

    private ComplaintDto sampleDto;
    private Complaint sampleComplaint;

    @BeforeEach
    void setUp() {
        sampleDto = ComplaintDto.builder()
                .userId(1)
                .category("Collection Delay")
                .description("Bin not collected for 3 days")
                .preferredContact("email")
                .evidenceUrl("http://example.com/image.jpg")
                .build();

        sampleComplaint = Complaint.builder()
                .complaintId(UUID.randomUUID().toString())
                .userId(1)
                .category("Collection Delay")
                .description("Bin not collected for 3 days")
                .status("OPEN")
                .createdAt(LocalDateTime.now())
                .preferredContact("email")
                .evidenceUrl("http://example.com/image.jpg")
                .build();
    }


    @Test
    void testCreateComplaint_Success() {
        when(complaintRepo.save(any(Complaint.class))).thenReturn(sampleComplaint);

        ResponseEntity<ApiResponse> response = complaintService.createComplaint(sampleDto);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("00", response.getBody().getStatusCode());
        assertEquals("Complaint created successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getContent());
        verify(complaintRepo, times(1)).save(any(Complaint.class));
    }

    @Test
    void testCreateComplaint_Failure() {
        when(complaintRepo.save(any(Complaint.class))).thenThrow(new RuntimeException("DB error"));

        ResponseEntity<ApiResponse> response = complaintService.createComplaint(sampleDto);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("99", response.getBody().getStatusCode());
        assertTrue(response.getBody().getMessage().contains("Failed to create complaint"));
        verify(complaintRepo, times(1)).save(any(Complaint.class));
    }


    @Test
    void testGetAllComplaints() {
        when(complaintRepo.findAll()).thenReturn(List.of(sampleComplaint));

        ResponseEntity<ApiResponse> response = complaintService.getAllComplaints();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("00", response.getBody().getStatusCode());
        assertEquals("All complaints retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getContent());
        verify(complaintRepo, times(1)).findAll();
    }


    @Test
    void testGetUserComplaints() {
        when(complaintRepo.findByUserId(1)).thenReturn(List.of(sampleComplaint));

        ResponseEntity<ApiResponse> response = complaintService.getUserComplaints(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("00", response.getBody().getStatusCode());
        assertEquals("User complaints retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getContent());
        verify(complaintRepo, times(1)).findByUserId(1);
    }


    @Test
    void testCloseComplaint_Found() {
        when(complaintRepo.findById(anyString())).thenReturn(Optional.of(sampleComplaint));
        when(complaintRepo.save(any(Complaint.class))).thenReturn(sampleComplaint);

        ResponseEntity<ApiResponse> response = complaintService.closeComplaint("some-id");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("00", response.getBody().getStatusCode());
        assertEquals("Complaint closed successfully", response.getBody().getMessage());
        assertEquals("CLOSE", ((ComplaintDto) response.getBody().getContent()).getStatus());
        verify(complaintRepo, times(1)).save(any(Complaint.class));
    }

    @Test
    void testCloseComplaint_NotFound() {
        when(complaintRepo.findById("missing")).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = complaintService.closeComplaint("missing");

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("01", response.getBody().getStatusCode());
        assertEquals("Complaint not found", response.getBody().getMessage());
        verify(complaintRepo, never()).save(any());
    }
}
