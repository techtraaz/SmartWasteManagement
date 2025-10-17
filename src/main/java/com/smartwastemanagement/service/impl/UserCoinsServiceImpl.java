package com.smartwastemanagement.service.impl;

import com.smartwastemanagement.entity.UserCoins;
import com.smartwastemanagement.repository.UserCoinRepo;
import com.smartwastemanagement.service.UserCoinsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCoinsServiceImpl implements UserCoinsService {

    private final UserCoinRepo userCoinRepo;

    @Override
    public UserCoins getUserCoins(Integer userId) {
        return userCoinRepo.findUserCoinsByUserId(userId);
    }

    @Override
    public void addCoins(UserCoins userCoins, Integer coinsToAdd) {
        userCoins.setCoins(userCoins.getCoins() + coinsToAdd);
        userCoinRepo.save(userCoins);
    }
}
