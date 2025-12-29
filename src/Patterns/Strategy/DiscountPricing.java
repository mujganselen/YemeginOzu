package Patterns.Strategy;

import Model.Order;

public class DiscountPricing implements PricingStrategy {

    private double discountPercentage;

    public DiscountPricing(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double calculatePrice(Order order) {
        double total = order.getTotalPrice();
        return total - (total * discountPercentage / 100);
    }

    @Override
    public String getStrategyName() {
        return "%" + discountPercentage + " İndirim";
    }
}
