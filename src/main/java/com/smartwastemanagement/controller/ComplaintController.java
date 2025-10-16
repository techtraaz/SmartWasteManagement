package com.smartwastemanagement.controller;

import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.dto.ComplaintDto;
import com.smartwastemanagement.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;


    @PostMapping
    public ResponseEntity<ApiResponse> createComplaint(@RequestBody ComplaintDto dto) {
        return complaintService.createComplaint(dto);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getUserComplaints(@PathVariable Integer userId) {
        return complaintService.getUserComplaints(userId);
    }


    @GetMapping("/admin/all")
    public ResponseEntity<ApiResponse> getAllComplaints() {
        return complaintService.getAllComplaints();
    }

    @PutMapping("/{complaintId}/close")
    public ResponseEntity<ApiResponse> closeComplaint(@PathVariable String complaintId) {
        return complaintService.closeComplaint(complaintId);
    }




}
