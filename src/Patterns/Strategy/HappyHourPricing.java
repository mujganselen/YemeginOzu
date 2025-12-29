package Patterns.Strategy;

import Model.Order;
import java.time.LocalTime;

public class HappyHourPricing implements PricingStrategy {

    private static final LocalTime HAPPY_HOUR_START = LocalTime.of(14, 0);
    private static final LocalTime HAPPY_HOUR_END = LocalTime.of(17, 0);
    private static final double DISCOUNT = 20.0;

    @Override
    public double calculatePrice(Order order) {
        LocalTime now = LocalTime.now();
        if (now.isAfter(HAPPY_HOUR_START) && now.isBefore(HAPPY_HOUR_END)) {
            double total = order.getTotalPrice();
            return total - (total * DISCOUNT / 100);
        }
        return order.getTotalPrice();
    }

    @Override
    public String getStrategyName() {
        return "Happy Hour (14:00-17:00, %20 İndirim)";
    }
}
