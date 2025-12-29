package Patterns.Strategy;

import Model.Order;

public class RegularPricing implements PricingStrategy {

    @Override
    public double calculatePrice(Order order) {
        return order.getTotalPrice();
    }

    @Override
    public String getStrategyName() {
        return "Normal Price";
    }
}
