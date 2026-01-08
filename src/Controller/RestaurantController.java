package Controller;

import Database.DAO.CategoryDAO;
import Database.DAO.IngredientDAO;
import Database.DAO.MenuItemDAO;
import Database.DAO.OrderDAO;

import Model.Category;
import Model.Ingredient;
import Model.MenuItem;
import Model.Order;
import Model.OrderItem;

import Patterns.Builder.OrderBuilder;
import Patterns.Observer.KitchenObserver;
import Patterns.Observer.OrderSubject;
import Patterns.Strategy.PricingStrategy;
import Patterns.Strategy.RegularPricing;

import java.util.List;

public class RestaurantController {

    private final CategoryDAO categoryDAO;
    private final MenuItemDAO menuItemDAO;
    private final IngredientDAO ingredientDAO;
    private final OrderDAO orderDAO;
    private Order lastCompletedOrder;

    private OrderBuilder currentOrderBuilder;
    private final OrderSubject orderSubject;
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

    public Order getLastCompletedOrder() {
        return lastCompletedOrder;
    }


    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }


    public List<MenuItem> getMenuItemsByCategory(int categoryId) {
        return menuItemDAO.getMenuItemsByCategory(categoryId);
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemDAO.getAllMenuItems();
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientDAO.getAllIngredients();
    }

    public List<Ingredient> getIngredientsForMenuItem(int menuItemId) {
        return ingredientDAO.getIngredientsForMenuItem(menuItemId);
    }

    public void startNewOrder(Order.OrderType orderType) {
        this.currentOrderBuilder = new OrderBuilder(orderType);
    }

    public void addItemToCurrentOrder(OrderItem item) {
        if (currentOrderBuilder == null) {
            startNewOrder(Order.OrderType.DINE_IN);
        }
        currentOrderBuilder.addItem(item);
    }

    public Order getCurrentOrder() {
        if (currentOrderBuilder == null) {
            return null;
        }
        return currentOrderBuilder.getOrder();
    }

    public double getCurrentOrderTotal() {
        if (currentOrderBuilder == null) {
            return 0.0;
        }
        Order order = currentOrderBuilder.getOrder();
        return pricingStrategy.calculatePrice(order);
    }

    public void rebuildCurrentOrder(List<OrderItem> cart, Order.OrderType orderType) {
        this.currentOrderBuilder = new OrderBuilder(orderType);

        for (OrderItem item : cart) {
            this.currentOrderBuilder.addItem(item);
        }
    }

    public boolean completeOrder() {
        if (currentOrderBuilder == null) {
            return false;
        }

        try {
            Order order = currentOrderBuilder.build();
            boolean saved = orderDAO.saveOrder(order);

            if (saved) {
                this.lastCompletedOrder = order;
                orderSubject.notifyOrderPlaced(order);
                currentOrderBuilder = null; // Reset for next order
                return true;
            }
            return false;

        } catch (IllegalStateException e) {
            System.err.println("Order could not be completed: " + e.getMessage());
            return false;
        }
    }

    public void cancelCurrentOrder() {
        currentOrderBuilder = null;
    }

    public void setPricingStrategy(PricingStrategy strategy) {
        this.pricingStrategy = strategy;
    }

    public PricingStrategy getPricingStrategy() {
        return pricingStrategy;
    }

    // Helper method
    public OrderItem createOrderItem(MenuItem menuItem) {
        return new OrderItem(menuItem);
    }
}
