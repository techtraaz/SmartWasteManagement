package com.smartwastemanagement.util.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CoinStrategyFactory {

    private final Map<String, CoinCalculationStrategy> strategies;

    @Autowired
    public CoinStrategyFactory(Map<String, CoinCalculationStrategy> strategies) {
        this.strategies = strategies;
    }

    public CoinCalculationStrategy getStrategy(String wasteType) {
        return strategies.getOrDefault(wasteType.toUpperCase(), strategies.get("FOOD"));
    }
}