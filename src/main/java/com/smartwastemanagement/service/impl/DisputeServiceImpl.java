package com.smartwastemanagement.service.impl;

import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.entity.Dispute;
import com.smartwastemanagement.repository.DisputeRepo;
import com.smartwastemanagement.service.DisputeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DisputeServiceImpl implements DisputeService {

    private final DisputeRepo disputeRepo;

    @Override
    public ResponseEntity<ApiResponse> createDispute(Integer userId, Integer paymentId, String reason) {
        Dispute dispute = Dispute.builder()
                .disputeId(UUID.randomUUID().toString())
                .userId(userId)
                .paymentId(paymentId)
                .reason(reason)
                .openAt(LocalDateTime.now())
                .status("PENDING")
                .build();

        Dispute saved = disputeRepo.save(dispute);

        ApiResponse response = new ApiResponse("00", "Dispute created successfully", saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<ApiResponse> resolveDispute(String disputeId) {
        Optional<Dispute> disputeOpt = disputeRepo.findById(disputeId);
        if (disputeOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("01", "Dispute not found", null));
        }

        Dispute dispute = disputeOpt.get();
        dispute.setStatus("RESOLVED");
        disputeRepo.save(dispute);

        ApiResponse response = new ApiResponse("00", "Dispute resolved successfully", dispute);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ApiResponse> getAllDisputes(){

        List<Dispute> disputes = disputeRepo.findAll();

        if (disputes.isEmpty()) {
            ApiResponse response = new ApiResponse("01", "Disputes not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse response = new ApiResponse("00", "Disputes found", disputes);
        return ResponseEntity.ok(response);

    }

}
