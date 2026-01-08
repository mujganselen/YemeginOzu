package View;

import Model.Order;
import Model.OrderItem;
import Patterns.Strategy.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CheckoutPanel extends JPanel {
    private MainFrame mainFrame;
    private JPanel orderSummaryPanel;
    private JLabel subtotalLabel;
    private JLabel discountLabel;
    private JLabel finalTotalLabel;
    private JComboBox<String> pricingCombo;

    private PricingStrategy currentStrategy;
    private static final String COUPON_10 = "SAVE10";
    private static final String COUPON_20 = "SAVE20";


    public CheckoutPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.currentStrategy = new RegularPricing();
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Center - order summary
        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        orderSummaryPanel = new JPanel();
        orderSummaryPanel.setLayout(new BoxLayout(orderSummaryPanel, BoxLayout.Y_AXIS));
        orderSummaryPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(orderSummaryPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Order Summary"));

        // Pricing options
        JPanel pricingPanel = createPricingPanel();

        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(pricingPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom - total and payment
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 140, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton backButton = new JButton("◀ Back to Cart");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(255, 165, 0));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> mainFrame.showCart());

        JLabel titleLabel = new JLabel("Checkout");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);

        panel.add(backButton, BorderLayout.WEST);
        panel.add(titleLabel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createPricingPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Pricing Option"));

        JLabel label = new JLabel("Price Type:");
        label.setFont(new Font("Arial", Font.BOLD, 14));

        String[] pricingOptions = {
                "Regular Price",
                "10% Discount",
                "20% Discount",
                "Happy Hour (14:00–17:00)"
        };

        pricingCombo = new JComboBox<>(pricingOptions);
        pricingCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        pricingCombo.setPreferredSize(new Dimension(250, 30));
        pricingCombo.addActionListener(e -> updatePricing());

        panel.add(label);
        panel.add(pricingCombo);

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Total summary
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.Y_AXIS));
        totalPanel.setBackground(new Color(245, 245, 245));

        subtotalLabel = new JLabel("Subtotal: 0.00 TL");
        subtotalLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        subtotalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        discountLabel = new JLabel("Discount: 0.00 TL");
        discountLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        discountLabel.setForeground(new Color(220, 53, 69));
        discountLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        finalTotalLabel = new JLabel("TOTAL: 0.00 TL");
        finalTotalLabel.setFont(new Font("Arial", Font.BOLD, 28));
        finalTotalLabel.setForeground(new Color(34, 139, 34));
        finalTotalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        totalPanel.add(subtotalLabel);
        totalPanel.add(Box.createVerticalStrut(5));
        totalPanel.add(discountLabel);
        totalPanel.add(Box.createVerticalStrut(10));
        totalPanel.add(finalTotalLabel);

        // Payment button
        JButton payButton = new JButton(" Complete Payment");
        payButton.setFont(new Font("Arial", Font.BOLD, 20));
        payButton.setPreferredSize(new Dimension(250, 60));
        payButton.setBackground(new Color(34, 139, 34));
        payButton.setForeground(Color.WHITE);
        payButton.setFocusPainted(false);
        payButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        payButton.addActionListener(e -> completePayment());

        panel.add(totalPanel, BorderLayout.WEST);
        panel.add(payButton, BorderLayout.EAST);

        return panel;
    }

    public void prepareCheckout() {
        orderSummaryPanel.removeAll();

        List<OrderItem> items = mainFrame.getOrderController().getCart();

        for (OrderItem item : items) {
            JLabel itemLabel = new JLabel(String.format("%dx %s - %.2f TL",
                    item.getQuantity(),
                    item.getMenuItem().getName(),
                    item.getTotalPrice()));
            itemLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            itemLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            itemLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            orderSummaryPanel.add(itemLabel);
        }

        updatePricing();
        orderSummaryPanel.revalidate();
        orderSummaryPanel.repaint();
    }

    private boolean askCouponAndValidate(String expectedCoupon, String discountLabel) {
        String input = JOptionPane.showInputDialog(
                this,
                "Enter coupon code for " + discountLabel + ":",
                "Coupon Required",
                JOptionPane.QUESTION_MESSAGE
        );

        if (input == null) { // user pressed cancel
            return false;
        }

        input = input.trim();

        return input.equalsIgnoreCase(expectedCoupon);
    }


    private void updatePricing() {
        String selected = (String) pricingCombo.getSelectedItem();

        switch (selected) {
            case "10% Discount": {
                boolean ok = askCouponAndValidate(COUPON_10, "10% Discount");
                if (ok) {
                    currentStrategy = new DiscountPricing(10);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid coupon code. Discount was not applied.",
                            "Coupon Error",
                            JOptionPane.WARNING_MESSAGE
                    );
                    pricingCombo.setSelectedItem("Regular Price");
                    currentStrategy = new RegularPricing();
                }
                break;
            }

            case "20% Discount": {
                boolean ok = askCouponAndValidate(COUPON_20, "20% Discount");
                if (ok) {
                    currentStrategy = new DiscountPricing(20);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid coupon code. Discount was not applied.",
                            "Coupon Error",
                            JOptionPane.WARNING_MESSAGE
                    );
                    pricingCombo.setSelectedItem("Regular Price");
                    currentStrategy = new RegularPricing();
                }
                break;
            }

            case "Happy Hour (14:00–17:00)":
                currentStrategy = new HappyHourPricing();
                break;

            default:
                currentStrategy = new RegularPricing();
        }

        mainFrame.getRestaurantController().setPricingStrategy(currentStrategy);
        updateTotals();
    }


    private void updateTotals() {
        List<OrderItem> cartItems = mainFrame.getOrderController().getCart();
        double subtotal = 0.0;
        for (OrderItem item : cartItems) {
            subtotal += item.getTotalPrice();
        }

        Order.OrderType orderType = mainFrame.getOrderController().getCurrentOrderType();
        Order tempOrder = new Order(orderType);
        tempOrder.setItems(cartItems);

        double finalTotal = mainFrame.getRestaurantController()
                .getPricingStrategy()
                .calculatePrice(tempOrder);

        double discount = subtotal - finalTotal;
        if (discount < 0) {
            discount = 0;
        }

        subtotalLabel.setText(String.format("Subtotal: %.2f TL", subtotal));
        discountLabel.setText(String.format("Discount: %.2f TL", discount));
        finalTotalLabel.setText(String.format("TOTAL: %.2f TL", finalTotal));
    }


    // biraz karışık oldu
    private void completePayment() {
        try {
            // 1) Sepeti al
            List<OrderItem> cart = mainFrame.getOrderController().getCart();

            if (cart == null || cart.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Your cart is empty! Please add items.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 2) Sipariş tipini doğru yerden al (OrderController)
            Order.OrderType orderType = mainFrame.getOrderController().getCurrentOrderType();

            // 3) RestaurantController içindeki Builder'ı sepete göre yeniden kur
            mainFrame.getRestaurantController().rebuildCurrentOrder(cart, orderType);

            // 4) Seçili pricing strategy ile final fiyatı hesapla
            double finalPrice = mainFrame.getRestaurantController().getCurrentOrderTotal();

            // 5) Siparişi kaydet + mutfağa bildir (Observer) -> parametresiz completeOrder()
            boolean success = mainFrame.getRestaurantController().completeOrder();

            if (success) {
                Order savedOrder = mainFrame.getRestaurantController().getLastCompletedOrder();
                String orderNo = (savedOrder != null) ? savedOrder.getOrderNumber() : "N/A";

                JOptionPane.showMessageDialog(this,
                        "✓ Your order has been placed successfully!\n" +
                                "Sent to the kitchen.\n" +
                                "Order No: " + orderNo + "\n" +
                                "Total: " + String.format("%.2f TL", finalPrice),
                        "Order Completed",
                        JOptionPane.INFORMATION_MESSAGE);

                mainFrame.getOrderController().clearCart();
                mainFrame.showPanel("WELCOME");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Order could not be saved. Please try again.",
                        "Error :( ",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            System.err.println("Payment error: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Hata: " + e.getMessage(),
                    "Hata",
                    JOptionPane.ERROR_MESSAGE);
        }
    }



}