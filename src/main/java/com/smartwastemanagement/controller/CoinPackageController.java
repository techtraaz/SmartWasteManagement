package com.smartwastemanagement.controller;

import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.dto.BuyPackageDto;
import com.smartwastemanagement.dto.RefundRequestDto;
import com.smartwastemanagement.entity.CoinPackage;
import com.smartwastemanagement.service.CoinPackageService;
import com.smartwastemanagement.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coinpackage")
@RequiredArgsConstructor
public class CoinPackageController {

    private final CoinPackageService  coinPackageService;
    private final RefundService refundService;

    @GetMapping("/getall")
    public ResponseEntity<ApiResponse> getAllCoins(){
        return coinPackageService.getAllPackages();
    }

    @PostMapping("/buy-package")
    public ResponseEntity<ApiResponse> buyPackage(@RequestBody BuyPackageDto buyreq){
        return coinPackageService.buyCoinPackage(buyreq.getUserId(), buyreq.getPackageId());
    }





}
