package OrderMenu;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class Order {
    private String orderId;
    private List<OrderItem> items;
    private String serviceType; // "dine-in" or "takeaway"
    private LocalDateTime orderTime;
    private String status;

    public Order(String orderId) {
        this.orderId = orderId;
        this.items = new ArrayList<>();
        this.orderTime = LocalDateTime.now();
        this.status = "Hazırlanıyor";
    }

    public String getOrderId() { return orderId; }
    public List<OrderItem> getItems() { return items; }
    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
    public LocalDateTime getOrderTime() { return orderTime; }
    public String getStatus() { return status; }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public double getTotalPrice() {
        return items.stream()
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();
    }
}