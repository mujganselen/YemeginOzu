package Controller;

import Model.Order;
import Model.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderController {

    private final List<OrderItem> cart;
    private Order currentOrder;

    public OrderController() {
        this.cart = new ArrayList<>();
    }

    public void createNewOrder(Order.OrderType orderType) {
        this.currentOrder = new Order(orderType);
        this.cart.clear();
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void addToCart(OrderItem item) {
        for (OrderItem existing : cart) {
            if (existing.getMenuItem().getId() == item.getMenuItem().getId()) {
                existing.setQuantity(existing.getQuantity() + item.getQuantity());
                return;
            }
        }
        cart.add(item);
    }

    public void removeFromCart(OrderItem item) {
        cart.remove(item);
    }

    public void updateItemQuantity(OrderItem item, int quantity) {
        if (quantity <= 0) {
            removeFromCart(item);
        } else {
            item.setQuantity(quantity);
        }
    }

    public List<OrderItem> getCart() {
        return new ArrayList<>(cart);
    }

    public double getCartTotal() {
        return cart.stream()
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();
    }

    public int getCartItemCount() {
        return cart.stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();
    }

    public void clearCart() {
        cart.clear();
    }

    public boolean isCartEmpty() {
        return cart.isEmpty();
    }
}
