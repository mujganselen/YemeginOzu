package View;

import Controller.RestaurantController;
import Controller.OrderController;
import Model.Order;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private RestaurantController restaurantController;
    private OrderController orderController;

    private WelcomePanel welcomePanel;
    private CategoryPanel categoryPanel;
    private MenuPanel menuPanel;
    private CartPanel cartPanel;
    private CheckoutPanel checkoutPanel;

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame() {
        initControllers();
        initUI();
        setupFrame();
    }

    private void initControllers() {
        this.restaurantController = new RestaurantController();
        this.orderController = new OrderController();
    }

    private void initUI() {
        setTitle("Yemegin Ozu - Restaurant Order System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Card Layout for switching panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create panels
        welcomePanel = new WelcomePanel(this);
        categoryPanel = new CategoryPanel(this);
        menuPanel = new MenuPanel(this);
        cartPanel = new CartPanel(this);
        checkoutPanel = new CheckoutPanel(this);

        // Add panels to card layout
        mainPanel.add(welcomePanel, "WELCOME");
        mainPanel.add(categoryPanel, "CATEGORY");
        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(cartPanel, "CART");
        mainPanel.add(checkoutPanel, "CHECKOUT");

        add(mainPanel);
    }

    private void setupFrame() {
        // Show welcome screen first
        showPanel("WELCOME");
    }

    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);

        // Refresh panel data when shown
        switch(panelName) {
            case "CATEGORY":
                categoryPanel.loadCategories();
                break;
            case "CART":
                cartPanel.refreshCart();
                break;
        }
    }

    public void startOrder(Order.OrderType orderType) {
        orderController.createNewOrder(orderType);
        restaurantController.startNewOrder(orderType);
        showPanel("CATEGORY");
    }

    public RestaurantController getRestaurantController() {
        return restaurantController;
    }

    public OrderController getOrderController() {
        return orderController;
    }

    public void showMenu(int categoryId) {
        menuPanel.loadMenuItems(categoryId);
        showPanel("MENU");
    }

    public void backToCategories() {
        showPanel("CATEGORY");
    }

    public void showCart() {
        showPanel("CART");
    }

    public void proceedToCheckout() {
        if (!orderController.isCartEmpty()) {
            checkoutPanel.prepareCheckout();
            showPanel("CHECKOUT");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Your cart is empty! Please add items.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}