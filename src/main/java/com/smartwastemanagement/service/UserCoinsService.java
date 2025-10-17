package com.smartwastemanagement.service;

import com.smartwastemanagement.entity.UserCoins;

public interface UserCoinsService {
    UserCoins getUserCoins(Integer userId);
    void addCoins(UserCoins userCoins, Integer coinsToAdd);
}
