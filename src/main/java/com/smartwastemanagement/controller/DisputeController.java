package com.smartwastemanagement.controller;

import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.dto.DisputeDto;
import com.smartwastemanagement.service.DisputeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dispute")
@RequiredArgsConstructor
public class DisputeController {

    private final DisputeService disputeService;


    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createDispute(@RequestBody DisputeDto disputeReq) {
        return disputeService
                .createDispute(disputeReq.getUserId(), disputeReq.getPaymentId(), disputeReq.getReason());
    }


    @PutMapping("/resolve/{disputeId}")
    public ResponseEntity<ApiResponse> resolveDispute(@PathVariable String disputeId) {
        return disputeService.resolveDispute(disputeId);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllDisputes() {
        return disputeService.getAllDisputes();
    }

}
