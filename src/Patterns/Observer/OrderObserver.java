package Patterns.Observer;

import OrderMenu.*;

// Observer Interface
public interface OrderObserver {
    void onOrderPlaced(Order order);
    void onOrderStatusChanged(Order order);
}