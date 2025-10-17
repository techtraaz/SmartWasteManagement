package com.smartwastemanagement.util.strategy;

import org.springframework.stereotype.Component;

@Component("PAPER")
public class PaperWasteStrategy implements CoinCalculationStrategy {
    @Override
    public int calculateCoins(double weight) {
        return (int) Math.ceil(weight * 1.5);
    }
}