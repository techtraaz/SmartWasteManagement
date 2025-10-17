package com.smartwastemanagement.service.impl;

import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.entity.Account;
import com.smartwastemanagement.entity.CoinPackage;
import com.smartwastemanagement.entity.Payment;
import com.smartwastemanagement.entity.UserCoins;
import com.smartwastemanagement.repository.CoinPackageRepo;
import com.smartwastemanagement.service.AccountService;
import com.smartwastemanagement.service.CoinPackageService;
import com.smartwastemanagement.service.PaymentService;
import com.smartwastemanagement.service.UserCoinsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoinPackageServiceImpl implements CoinPackageService {

    private final CoinPackageRepo coinPackageRepo;
    private final AccountService accountService;
    private final UserCoinsService userCoinsService;
    private final PaymentService paymentService;

    @Override
    public ResponseEntity<ApiResponse> getAllPackages() {
        List<CoinPackage> packages = coinPackageRepo.findAll();
        if (packages.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse("00", "No packages found", null));
        }
        return ResponseEntity.ok(new ApiResponse("02", "Available packages", packages));
    }

    @Override
    public ResponseEntity<ApiResponse> buyCoinPackage(Integer userId, Integer packageId) {

        CoinPackage coinPackage = coinPackageRepo.findCoinPackageById(packageId);
        if (coinPackage == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("01", "Coin package not found", null));
        }

        Account account = accountService.getAccountByUserId(userId);
        if (account.getBalance() < coinPackage.getPrice()) {
            return ResponseEntity.ok(new ApiResponse("00", "Insufficient balance", null));
        }


        accountService.deductBalance(account, coinPackage.getPrice());

        UserCoins userCoins = userCoinsService.getUserCoins(userId);
        userCoinsService.addCoins(userCoins, coinPackage.getCoins());


        Payment payment = paymentService.createPayment(userId, coinPackage.getPrice(), "CARD");

        return ResponseEntity.ok(new ApiResponse("02", "Package bought successfully", payment));
    }
}
