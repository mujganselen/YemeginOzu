package Patterns.Strategy;

import OrderMenu.*;

public class RegularPricing implements PricingStrategy {
    @Override
    public double calculatePrice(Order order) {
        return order.getTotalPrice();
    }

    @Override
    public String getStrategyName() {
        return "Normal Fiyat";
    }
}