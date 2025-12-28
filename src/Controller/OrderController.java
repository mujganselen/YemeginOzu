
package Controller;

import OrderMenu.*;
import java.util.ArrayList;
import java.util.List;

public class OrderController {
    private List<OrderItem> cart;

    public OrderController() {
        this.cart = new ArrayList<>();
    }

    public void addToCart(OrderItem item) {
        // Check if same item exists
        for (OrderItem existing : cart) {
            if (existing.getMenuItem().getName().equals(item.getMenuItem().getName())) {
                existing.setQuantity(existing.getQuantity() + item.getQuantity());
                System.out.println("🔄 Miktar güncellendi: " + item.getMenuItem().getName());
                return;
            }
        }
        cart.add(item);
        System.out.println("🛒 Sepete eklendi: " + item);
    }

    public void removeFromCart(OrderItem item) {
        cart.remove(item);
        System.out.println("🗑️ Sepetten çıkarıldı: " + item.getMenuItem().getName());
    }

    public void updateItemQuantity(OrderItem item, int quantity) {
        if (quantity <= 0) {
            removeFromCart(item);
        } else {
            item.setQuantity(quantity);
            System.out.println("🔢 Miktar güncellendi: " + quantity);
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
        System.out.println("🧹 Sepet temizlendi");
    }

    public boolean isCartEmpty() {
        return cart.isEmpty();
    }

    public Order buildOrderFromCart(String serviceType) {
        OrderBuilder builder = new OrderBuilder();
        builder.setServiceType(serviceType);

        for (OrderItem item : cart) {
            builder.addMenuItem(item.getMenuItem(), item.getQuantity());

            // Add customizations
            for (String ing : item.getAddedIngredients()) {
                builder.addIngredient(ing);
            }
            for (String ing : item.getRemovedIngredients()) {
                builder.removeIngredient(ing);
            }
        }

        return builder.build();
    }
}