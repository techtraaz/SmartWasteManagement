package com.smartwastemanagement.util.strategy;

import org.springframework.stereotype.Component;



@Component("FOOD")
public class FoodWasteStrategy implements CoinCalculationStrategy {
    @Override
    public int calculateCoins(double weight) {
        return (int) Math.ceil(weight * 2); // e.g. 2 coins per kg
    }
}