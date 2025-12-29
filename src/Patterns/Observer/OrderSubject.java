package Patterns.Observer;

import Model.Order;
import java.util.ArrayList;
import java.util.List;

public class OrderSubject {
    private List<OrderObserver> observers = new ArrayList<>();

    public void attach(OrderObserver observer) {
        observers.add(observer);
    }

    public void detach(OrderObserver observer) {
        observers.remove(observer);
    }

    public void notifyOrderPlaced(Order order) {
        for (OrderObserver observer : observers) {
            observer.onOrderPlaced(order);
        }
    }

    public void notifyOrderStatusChanged(Order order) {
        for (OrderObserver observer : observers) {
            observer.onOrderStatusChanged(order);
        }
    }
}