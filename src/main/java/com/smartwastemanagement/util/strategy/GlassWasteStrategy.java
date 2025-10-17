package com.smartwastemanagement.util.strategy;


import org.springframework.stereotype.Component;

@Component("GLASS")
public class GlassWasteStrategy implements CoinCalculationStrategy {
    @Override
    public int calculateCoins(double weight) {
        return (int) Math.ceil(weight * 2.5);
    }
}