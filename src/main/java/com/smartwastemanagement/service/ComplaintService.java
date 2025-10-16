package com.smartwastemanagement.service;

import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.dto.ComplaintDto;
import org.springframework.http.ResponseEntity;

public interface ComplaintService {

    ResponseEntity<ApiResponse> createComplaint(ComplaintDto dto);

    ResponseEntity<ApiResponse> getUserComplaints(Integer userId);

    ResponseEntity<ApiResponse> getAllComplaints(); // For admin

    ResponseEntity<ApiResponse> closeComplaint(String complaintId);



}
