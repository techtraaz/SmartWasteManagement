package com.smartwastemanagement.repository;

import com.smartwastemanagement.entity.UserCoins;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCoinRepo extends JpaRepository<UserCoins,Integer> {


   UserCoins findUserCoinsByUserId(Integer userId);

}
