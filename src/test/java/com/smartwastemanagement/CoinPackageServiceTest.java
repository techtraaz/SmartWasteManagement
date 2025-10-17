package com.smartwastemanagement;


import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.entity.*;
import com.smartwastemanagement.repository.CoinPackageRepo;
import com.smartwastemanagement.service.AccountService;
import com.smartwastemanagement.service.PaymentService;
import com.smartwastemanagement.service.UserCoinsService;
import com.smartwastemanagement.service.impl.CoinPackageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoinPackageServiceTest {

    @Mock
    private CoinPackageRepo coinPackageRepo;

    @Mock
    private AccountService accountService;

    @Mock
    private UserCoinsService userCoinsService;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private CoinPackageServiceImpl coinPackageService;


    @Test
    void testGetAllPackages_WhenEmpty() {
        when(coinPackageRepo.findAll()).thenReturn(List.of());

        ResponseEntity<ApiResponse> response = coinPackageService.getAllPackages();

        assertEquals("00", response.getBody().getStatusCode());
        assertEquals("No packages found", response.getBody().getMessage());
        verify(coinPackageRepo, times(1)).findAll();
    }

    @Test
    void testGetAllPackages_WhenDataExists() {
        CoinPackage pkg = CoinPackage.builder().Id(1).name("Gold").price(100.0).coins(1000).build();
        when(coinPackageRepo.findAll()).thenReturn(List.of(pkg));

        ResponseEntity<ApiResponse> response = coinPackageService.getAllPackages();

        assertEquals("02", response.getBody().getStatusCode());
        assertEquals("Available packages", response.getBody().getMessage());
        assertNotNull(response.getBody().getContent());
        verify(coinPackageRepo, times(1)).findAll();
    }


    @Test
    void testBuyCoinPackage_PackageNotFound() {
        when(coinPackageRepo.findCoinPackageById(99)).thenReturn(null);

        ResponseEntity<ApiResponse> response = coinPackageService.buyCoinPackage(1, 99);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("01", response.getBody().getStatusCode());
        assertEquals("Coin package not found", response.getBody().getMessage());
    }

    @Test
    void testBuyCoinPackage_InsufficientBalance() {
        CoinPackage pkg = CoinPackage.builder().Id(1).name("Gold").price(500.0).coins(1000).build();
        Account acc = new Account();
        acc.setBalance(100.0);

        when(coinPackageRepo.findCoinPackageById(1)).thenReturn(pkg);
        when(accountService.getAccountByUserId(1)).thenReturn(acc);

        ResponseEntity<ApiResponse> response = coinPackageService.buyCoinPackage(1, 1);

        assertEquals("00", response.getBody().getStatusCode());
        assertEquals("Insufficient balance", response.getBody().getMessage());
        verify(accountService, never()).deductBalance(any(), any());
    }

    @Test
    void testBuyCoinPackage_SuccessfulPurchase() {
        // given
        CoinPackage pkg = CoinPackage.builder().Id(1).name("Gold").price(100.0).coins(500).build();
        Account acc = new Account();
        acc.setBalance(500.0);
        UserCoins coins = new UserCoins();
        coins.setUserId(1);
        coins.setCoins(100);

        Payment mockPayment = Payment.builder().paymentId(1).userId(1).amount(100.0).paymentMethod("CARD").build();

        when(coinPackageRepo.findCoinPackageById(1)).thenReturn(pkg);
        when(accountService.getAccountByUserId(1)).thenReturn(acc);
        when(userCoinsService.getUserCoins(1)).thenReturn(coins);
        when(paymentService.createPayment(1, 100.0, "CARD")).thenReturn(mockPayment);


        ResponseEntity<ApiResponse> response = coinPackageService.buyCoinPackage(1, 1);


        assertEquals("02", response.getBody().getStatusCode());
        assertEquals("Package bought successfully", response.getBody().getMessage());
        assertEquals(mockPayment, response.getBody().getContent());

        verify(accountService).deductBalance(acc, 100.0);
        verify(userCoinsService).addCoins(coins, 500);
        verify(paymentService).createPayment(1, 100.0, "CARD");
    }
}
