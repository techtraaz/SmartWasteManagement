package com.smartwastemanagement.service;

import com.smartwastemanagement.entity.Account;

public interface AccountService {
    Account getAccountByUserId(Integer userId);
    void deductBalance(Account account, Double amount);
}
