package Patterns.Observer;

import Model.Order;

// Observer Interface
public interface OrderObserver {
    void onOrderPlaced(Order order);
    void onOrderStatusChanged(Order order);
}
