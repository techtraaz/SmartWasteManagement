package com.smartwastemanagement.service;

import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.entity.Bin;
import org.springframework.http.ResponseEntity;

public interface BinService {


    ResponseEntity<ApiResponse> readBinData(String binId);


}
