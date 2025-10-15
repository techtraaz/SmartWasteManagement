package com.smartwastemanagement.service.impl;

import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.entity.Account;
import com.smartwastemanagement.entity.CoinPackage;
import com.smartwastemanagement.entity.Payment;
import com.smartwastemanagement.entity.UserCoins;
import com.smartwastemanagement.repository.AccountRepo;
import com.smartwastemanagement.repository.CoinPackageRepo;
import com.smartwastemanagement.repository.PaymentRepo;
import com.smartwastemanagement.repository.UserCoinRepo;
import com.smartwastemanagement.service.CoinPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoinPackageServiceImpl implements CoinPackageService {

    private final CoinPackageRepo packagerepo;
    private final AccountRepo accountrepo;
    private final UserCoinRepo  usercoinrepo;
    private final PaymentRepo paymentrepo;

    @Override
    public ResponseEntity<ApiResponse>  getAllPackages() {

        List<CoinPackage> coinPackages = packagerepo.findAll();

        if(coinPackages.isEmpty()){
            ApiResponse response = new ApiResponse("00","no packages found",null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        ApiResponse response = new ApiResponse("02","available packages",coinPackages);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<ApiResponse> buyCoinPackage(Integer userId , Integer packageId) {

        Account bankacc = accountrepo.findByAccountId(userId);
        CoinPackage pc = packagerepo.findCoinPackageById(packageId);

        ApiResponse response;

        if (bankacc.getBalance() < pc.getPrice()) {
            response = new ApiResponse("00", "insufficient balance", null);
        } else {

            CoinPackage coinPackage = packagerepo.findCoinPackageById(packageId);
            UserCoins userCoins = usercoinrepo.findUserCoinsByUserId(userId);
            Integer cointCount = userCoins.getCoins() + coinPackage.getCoins();
            userCoins.setCoins(cointCount);
            bankacc.setBalance(bankacc.getBalance() - pc.getPrice());
            accountrepo.save(bankacc);
            usercoinrepo.save(userCoins);
            Payment packagePayment = makePayment(userId, pc.getPrice(), "card");
            response = new ApiResponse("02", "package bought sucessfully", packagePayment);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    public Payment makePayment(Integer userId, Double amount , String paymentMethod) {
        LocalDateTime dateNow = LocalDateTime.now();
        Payment payment = new Payment();
        payment.setUserId(userId);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentDate(dateNow);
        paymentrepo.save(payment);
        return payment;
    }






}
