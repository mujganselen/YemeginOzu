package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    public enum OrderType { DINE_IN, TAKEAWAY }
    public enum OrderStatus { PENDING, PREPARING, READY, COMPLETED, CANCELLED }

    private int id;
    private String orderNumber;
    private OrderType orderType;
    private List<OrderItem> items;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    public Order() {
        this.items = new ArrayList<>();
        this.status = OrderStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public Order(OrderType orderType) {
        this();
        this.orderType = orderType;
        this.orderNumber = generateOrderNumber();
    }

    private String generateOrderNumber() {
        return "ORD" + System.currentTimeMillis();
    }

    public double getTotalPrice() {
        return items.stream()
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();
    }

    public void addItem(OrderItem item) {
        // Check if same item already exists
        for (OrderItem existing : items) {
            if (existing.getMenuItem().getId() == item.getMenuItem().getId() &&
                    existing.getAddedIngredients().equals(item.getAddedIngredients()) &&
                    existing.getRemovedIngredients().equals(item.getRemovedIngredients())) {
                existing.setQuantity(existing.getQuantity() + item.getQuantity());
                return;
            }
        }
        items.add(item);
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }

    public OrderType getOrderType() { return orderType; }
    public void setOrderType(OrderType orderType) { this.orderType = orderType; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
}