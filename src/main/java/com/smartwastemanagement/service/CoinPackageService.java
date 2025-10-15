package com.smartwastemanagement.service;

import com.smartwastemanagement.dto.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface CoinPackageService {

    ResponseEntity<ApiResponse> getAllPackages();

    ResponseEntity<ApiResponse> buyCoinPackage(Integer userId , Integer packageId);


}
