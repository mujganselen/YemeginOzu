package Patterns.Observer;

import Model.Order;

public class KitchenObserver implements OrderObserver {

    private String kitchenName;

    public KitchenObserver(String kitchenName) {
        this.kitchenName = kitchenName;
    }

    @Override
    public void onOrderPlaced(Order order) {
        System.out.println("[" + kitchenName + "] NEW ORDER: " + order.getOrderNumber());
        System.out.println("Order Type: " + order.getOrderType());
        System.out.println("Item Count: " + order.getItems().size());
        System.out.println("Total: " + order.getTotalPrice() + " TL");
        System.out.println("─────────────────────────────────");
    }

    @Override
    public void onOrderStatusChanged(Order order) {
        System.out.println("[" + kitchenName + "] ORDER STATUS CHANGED: " +
                order.getOrderNumber() + " -> " + order.getStatus());
    }
}
