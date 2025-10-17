package com.smartwastemanagement.service.impl;

import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.dto.ComplaintDto;
import com.smartwastemanagement.entity.Complaint;
import com.smartwastemanagement.repository.ComplaintRepo;
import com.smartwastemanagement.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepo complaintRepository;

    @Override
    public ResponseEntity<ApiResponse> createComplaint(ComplaintDto dto) {
        try {
            Complaint complaint = Complaint.builder()
                    .complaintId(UUID.randomUUID().toString())
                    .userId(dto.getUserId())
                    .category(dto.getCategory())
                    .description(dto.getDescription())
                    .status("OPEN")
                    .evidenceUrl(dto.getEvidenceUrl())
                    .preferredContact(dto.getPreferredContact())
                    .build();

            Complaint saved = complaintRepository.save(complaint);

            ApiResponse response = new ApiResponse("00", "Complaint created successfully", mapToDTO(saved));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            ApiResponse response = new ApiResponse("99", "Failed to create complaint: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> getUserComplaints(Integer userId) {
        List<Complaint> complaints = complaintRepository.findByUserId(userId);


        ApiResponse response = new ApiResponse("00", "User complaints retrieved successfully", complaints);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ApiResponse> getAllComplaints() {
        List<Complaint> complaints = complaintRepository.findAll();


        ApiResponse response = new ApiResponse("00", "All complaints retrieved successfully", complaints);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ApiResponse> closeComplaint(String complaintId) {
        return complaintRepository.findById(complaintId).map(complaint -> {
            complaint.setStatus("CLOSE");
            complaintRepository.save(complaint);

            ApiResponse response = new ApiResponse("00", "Complaint closed successfully", mapToDTO(complaint));
            return ResponseEntity.ok(response);

        }).orElseGet(() -> {
            ApiResponse response = new ApiResponse("01", "Complaint not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        });
    }


    private ComplaintDto mapToDTO(Complaint complaint) {
        return ComplaintDto.builder()
                .complaintId(complaint.getComplaintId())
                .userId(complaint.getUserId())
                .category(complaint.getCategory())
                .description(complaint.getDescription())
                .status(complaint.getStatus())
                .evidenceUrl(complaint.getEvidenceUrl())
                .createdAt(complaint.getCreatedAt())
                .preferredContact(complaint.getPreferredContact())
                .build();
    }
}
