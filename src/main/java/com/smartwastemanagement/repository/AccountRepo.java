package com.smartwastemanagement.repository;

import com.smartwastemanagement.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account,Integer> {

    Account findByAccountId(Integer accountId);


}
