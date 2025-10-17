package com.smartwastemanagement;


import com.smartwastemanagement.entity.Account;
import com.smartwastemanagement.repository.AccountRepo;
import com.smartwastemanagement.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    @Mock
    private AccountRepo accountRepo;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAccountByUserId() {

        Account mockAccount = new Account();
        mockAccount.setAccountId(1);
        mockAccount.setBalance(100.0);

        when(accountRepo.findByAccountId(1)).thenReturn(mockAccount);


        Account result = accountService.getAccountByUserId(1);


        assertNotNull(result);
        assertEquals(1, result.getAccountId());
        assertEquals(100.0, result.getBalance());
        verify(accountRepo, times(1)).findByAccountId(1);
    }

    @Test
    void testDeductBalance() {

        Account account = new Account();
        account.setAccountId(1);
        account.setBalance(200.0);


        accountService.deductBalance(account, 50.0);


        assertEquals(150.0, account.getBalance());
        verify(accountRepo, times(1)).save(account);
    }
}
