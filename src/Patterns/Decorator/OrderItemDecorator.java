package Patterns.Decorator;

import Model.*;
public abstract class OrderItemDecorator {
    protected OrderItem orderItem;

    public OrderItemDecorator(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public abstract double getPrice();
    public abstract String getDescription();
}