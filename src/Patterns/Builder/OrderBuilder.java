package Patterns.Builder;

import Model.*;

public class OrderBuilder {
    private Order order;

    public OrderBuilder() {
    }

    public OrderBuilder(Order.OrderType orderType) {
        this.order = new Order(orderType);
    }

    public OrderBuilder setServiceType(String serviceType) {
        Order.OrderType type = serviceType.equalsIgnoreCase("takeaway")
                ? Order.OrderType.TAKEAWAY
                : Order.OrderType.DINE_IN;

        this.order = new Order(type);
        return this;
    }

    public OrderBuilder addItem(OrderItem item) {
        if (order == null) {
            this.order = new Order(Order.OrderType.DINE_IN);
        }
        order.addItem(item);
        return this;
    }

    public OrderBuilder setOrderType(Order.OrderType orderType) {
        if (order != null) {
            order.setOrderType(orderType);
        }
        return this;
    }

    public OrderBuilder addIngredient(Ingredient ingredient) {
        if (order != null && !order.getItems().isEmpty()) {
            OrderItem lastItem = order.getItems().get(order.getItems().size() - 1);
            lastItem.addIngredient(ingredient);
        }
        return this;
    }

    public OrderBuilder removeIngredient(Ingredient ingredient) {
        if (order != null && !order.getItems().isEmpty()) {
            OrderItem lastItem = order.getItems().get(order.getItems().size() - 1);
            lastItem.removeIngredient(ingredient);
        }
        return this;
    }

    public Order build() {
        if (order == null) {
            throw new IllegalStateException("Order has not been created!");
        }
        if (order.getItems().isEmpty()) {
            throw new IllegalStateException("The order must contain at least one item!");
        }
        return order;
    }


    public Order getOrder() {
        return order;
    }
}