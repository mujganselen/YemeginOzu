package Controller;

import Model.*;
import java.util.ArrayList;
import java.util.List;

public class OrderController {
    private List<OrderItem> cart;
    private Order.OrderType currentOrderType;

    public OrderController() {
        this.cart = new ArrayList<>();
        this.currentOrderType = Order.OrderType.DINE_IN;
    }

    public void createNewOrder(Order.OrderType orderType) {
        this.currentOrderType = orderType;
        this.cart.clear();
        System.out.println("New order created: " + orderType);
    }

    public void addToCart(OrderItem item) {
        // Check if same item with same customizations exists
        for (OrderItem existing : cart) {
            if (isSameItem(existing, item)) {
                existing.setQuantity(existing.getQuantity() + item.getQuantity());
                System.out.println("Quantity updated: " + item.getMenuItem().getName());
                return;
            }
        }
        cart.add(item);
        System.out.println("🛒 Added to cart: " + item);
    }

    private boolean isSameItem(OrderItem item1, OrderItem item2) {
        if (!item1.getMenuItem().equals(item2.getMenuItem())) {
            return false;
        }

        if (item1.getAddedIngredients().size() != item2.getAddedIngredients().size()) {
            return false;
        }

        if (item1.getRemovedIngredients().size() != item2.getRemovedIngredients().size()) {
            return false;
        }

        return item1.getAddedIngredients().containsAll(item2.getAddedIngredients()) &&
                item1.getRemovedIngredients().containsAll(item2.getRemovedIngredients());
    }

    public void removeFromCart(OrderItem item) {
        cart.remove(item);
        System.out.println("Removed from cart: " + item.getMenuItem().getName());
    }

    public void updateItemQuantity(OrderItem item, int quantity) {
        if (quantity <= 0) {
            removeFromCart(item);
        } else {
            item.setQuantity(quantity);
            System.out.println("Quantity updated: " + quantity);
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
        System.out.println("Cart cleared");
    }

    public boolean isCartEmpty() {
        return cart.isEmpty();
    }

    public Order.OrderType getCurrentOrderType() {
        return currentOrderType;
    }

    public void setCurrentOrderType(Order.OrderType orderType) {
        this.currentOrderType = orderType;
    }
}