package Controller;

import Database.DAO.*;
import OrderMenu.*;
import Model.*;
import Patterns.Observer.*;
import Patterns.Strategy.*;
import java.util.List;

public class RestaurantController {
    private CategoryDAO categoryDAO;
    private MenuItemDAO menuItemDAO;
    private IngredientDAO ingredientDAO;
    private OrderDAO orderDAO;

    private Order currentOrder;
    private OrderSubject orderSubject;
    private PricingStrategy pricingStrategy;

    public RestaurantController() {
        this.categoryDAO = new CategoryDAO();
        this.menuItemDAO = new MenuItemDAO();
        this.ingredientDAO = new IngredientDAO();
        this.orderDAO = new OrderDAO();

        // Observer pattern - Kitchen notification
        this.orderSubject = new OrderSubject();
        this.orderSubject.attach(new KitchenObserver("Ana Mutfak"));

        // Default pricing strategy
        this.pricingStrategy = new RegularPricing();
    }

    // ===== Category Operations =====
    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    // ===== Menu Operations =====
    public List<MenuItem> getMenuItemsByCategory(String categoryName) {
        return menuItemDAO.getMenuItemsByCategory(categoryName);
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemDAO.getAllMenuItems();
    }

    // ===== Ingredient Operations =====
    public List<Ingredient> getAllIngredients() {
        return ingredientDAO.getAllIngredients();
    }

    // ===== Order Operations =====
    public void startNewOrder(String serviceType) {
        OrderBuilder builder = new OrderBuilder();
        builder.setServiceType(serviceType);
        this.currentOrder = builder.build();
        System.out.println("🆕 Yeni sipariş başlatıldı: " + currentOrder.getOrderId());
    }

    public void addItemToCurrentOrder(MenuItem menuItem, int quantity) {
        if (currentOrder == null) {
            startNewOrder("dine-in");
        }
        OrderItem item = new OrderItem(menuItem, quantity);
        currentOrder.addItem(item);
        System.out.println("➕ Sepete eklendi: " + menuItem.getName() + " x" + quantity);
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public double getCurrentOrderTotal() {
        if (currentOrder == null) {
            return 0.0;
        }
        return pricingStrategy.calculatePrice(currentOrder);
    }

    public boolean completeOrder() {
        if (currentOrder == null || currentOrder.getItems().isEmpty()) {
            System.err.println("❌ Sipariş boş!");
            return false;
        }

        boolean saved = orderDAO.saveOrder(currentOrder);

        if (saved) {
            orderSubject.notifyOrderPlaced(currentOrder);
            currentOrder = null; // Reset for next order
            return true;
        }
        return false;
    }

    public void cancelCurrentOrder() {
        currentOrder = null;
        System.out.println("❌ Sipariş iptal edildi");
    }

    // ===== Pricing Strategy =====
    public void setPricingStrategy(PricingStrategy strategy) {
        this.pricingStrategy = strategy;
        System.out.println("💰 Fiyatlandırma değişti: " + strategy.getStrategyName());
    }

    public PricingStrategy getPricingStrategy() {
        return pricingStrategy;
    }

    public String getPricingStrategyName() {
        return pricingStrategy.getStrategyName();
    }
}