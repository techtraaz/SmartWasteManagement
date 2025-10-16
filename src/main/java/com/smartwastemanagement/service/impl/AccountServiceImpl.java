package com.smartwastemanagement.service.impl;

import com.smartwastemanagement.entity.Account;
import com.smartwastemanagement.repository.AccountRepo;
import com.smartwastemanagement.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;

    @Override
    public Account getAccountByUserId(Integer userId) {
        return accountRepo.findByAccountId(userId);
    }

    @Override
    public void deductBalance(Account account, Double amount) {
        account.setBalance(account.getBalance() - amount);
        accountRepo.save(account);
    }
}
