package Patterns.Strategy;

import Model.Order;

// Strategy Interface
public interface PricingStrategy {
    double calculatePrice(Order order);
    String getStrategyName();
}
