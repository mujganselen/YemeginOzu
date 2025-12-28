package Patterns.Strategy;

import OrderMenu.*;

// Strategy Interface
public interface PricingStrategy {
    double calculatePrice(Order order);
    String getStrategyName();
}