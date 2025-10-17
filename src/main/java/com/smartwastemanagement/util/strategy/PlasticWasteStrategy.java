package com.smartwastemanagement.util.strategy;

import org.springframework.stereotype.Component;


@Component("PLASTIC")
public class PlasticWasteStrategy implements CoinCalculationStrategy {
    @Override
    public int calculateCoins(double weight) {
        return (int) Math.ceil(weight * 3); // more coins for recycling
    }
}