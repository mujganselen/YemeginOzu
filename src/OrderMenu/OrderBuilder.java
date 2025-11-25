package OrderMenu;

class OrderBuilder {
    private Order order;
    private OrderItem currentItem;

    public OrderBuilder() {
        this.order = new Order(generateOrderId());
    }

    private String generateOrderId() {
        return "ORD-" + System.currentTimeMillis();
    }

    public OrderBuilder addMenuItem(MenuItem menuItem, int quantity) {
        currentItem = new OrderItem(menuItem, quantity);
        order.addItem(currentItem);
        return this;
    }

    public OrderBuilder addIngredient(String ingredient) {
        if (currentItem != null) {
            currentItem.addIngredient(ingredient);
        }
        return this;
    }

    public OrderBuilder removeIngredient(String ingredient) {
        if (currentItem != null) {
            currentItem.removeIngredient(ingredient);
        }
        return this;
    }

    public OrderBuilder setServiceType(String serviceType) {
        order.setServiceType(serviceType);
        return this;
    }

    public Order build() {
        return order;
    }
}
