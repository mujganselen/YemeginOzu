package View;

import Model.OrderItem;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CartPanel extends JPanel {
    private MainFrame mainFrame;
    private JPanel cartItemsPanel;
    private JLabel totalLabel;
    private JButton checkoutButton;

    public CartPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Cart items
        cartItemsPanel = new JPanel();
        cartItemsPanel.setLayout(new BoxLayout(cartItemsPanel, BoxLayout.Y_AXIS));
        cartItemsPanel.setBackground(Color.WHITE);
        cartItemsPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JScrollPane scrollPane = new JScrollPane(cartItemsPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel - total and checkout
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(34, 139, 34));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton backButton = new JButton("◀ Devam Et");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(46, 160, 46));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> mainFrame.backToCategories());

        JLabel titleLabel = new JLabel("🛒 Sepetim");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);

        panel.add(backButton, BorderLayout.WEST);
        panel.add(titleLabel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Total
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalPanel.setBackground(new Color(245, 245, 245));

        totalLabel = new JLabel("Toplam: 0.00 TL");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 28));
        totalLabel.setForeground(new Color(34, 139, 34));
        totalPanel.add(totalLabel);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonPanel.setBackground(new Color(245, 245, 245));

        JButton clearButton = new JButton("🗑️ Sepeti Temizle");
        clearButton.setFont(new Font("Arial", Font.PLAIN, 16));
        clearButton.setPreferredSize(new Dimension(180, 50));
        clearButton.setBackground(new Color(220, 53, 69));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearButton.addActionListener(e -> clearCart());

        checkoutButton = new JButton("✓ Ödemeye Geç");
        checkoutButton.setFont(new Font("Arial", Font.BOLD, 18));
        checkoutButton.setPreferredSize(new Dimension(200, 50));
        checkoutButton.setBackground(new Color(255, 140, 0));
        checkoutButton.setForeground(Color.WHITE);
        checkoutButton.setFocusPainted(false);
        checkoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkoutButton.addActionListener(e -> mainFrame.proceedToCheckout());

        buttonPanel.add(clearButton);
        buttonPanel.add(checkoutButton);

        panel.add(totalPanel, BorderLayout.WEST);
        panel.add(buttonPanel, BorderLayout.EAST);
        return panel;
    }

    public void refreshCart() {
        cartItemsPanel.removeAll();

        List<OrderItem> cartItems = mainFrame.getOrderController().getCart();

        if (cartItems.isEmpty()) {
            showEmptyCart();
        } else {
            for (OrderItem item : cartItems) {
                JPanel itemPanel = createCartItemPanel(item);
                cartItemsPanel.add(itemPanel);
                cartItemsPanel.add(Box.createVerticalStrut(10));
            }
        }

        updateTotal();
        cartItemsPanel.revalidate();
        cartItemsPanel.repaint();
    }

    private void showEmptyCart() {
        JPanel emptyPanel = new JPanel();
        emptyPanel.setLayout(new BoxLayout(emptyPanel, BoxLayout.Y_AXIS));
        emptyPanel.setBackground(Color.WHITE);
        emptyPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));

        JLabel iconLabel = new JLabel("🛒");
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel messageLabel = new JLabel("Sepetiniz boş");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        messageLabel.setForeground(Color.GRAY);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subLabel = new JLabel("Lütfen menüden ürün ekleyin");
        subLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subLabel.setForeground(Color.LIGHT_GRAY);
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        emptyPanel.add(iconLabel);
        emptyPanel.add(Box.createVerticalStrut(20));
        emptyPanel.add(messageLabel);
        emptyPanel.add(Box.createVerticalStrut(10));
        emptyPanel.add(subLabel);

        cartItemsPanel.add(emptyPanel);
        checkoutButton.setEnabled(false);
    }

    private JPanel createCartItemPanel(OrderItem item) {
        JPanel panel = new JPanel(new BorderLayout(15, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // Item info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(item.getMenuItem().getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Customization info
        StringBuilder customText = new StringBuilder();
        if (!item.getAddedIngredients().isEmpty()) {
            customText.append("Eklenen: ");
            item.getAddedIngredients().forEach(ing ->
                    customText.append(ing.getName()).append(", "));
            customText.setLength(customText.length() - 2);
        }
        if (!item.getRemovedIngredients().isEmpty()) {
            if (customText.length() > 0) customText.append(" | ");
            customText.append("Çıkarılan: ");
            item.getRemovedIngredients().forEach(ing ->
                    customText.append(ing.getName()).append(", "));
            customText.setLength(customText.length() - 2);
        }

        if (customText.length() > 0) {
            JLabel customLabel = new JLabel(customText.toString());
            customLabel.setFont(new Font("Arial", Font.ITALIC, 12));
            customLabel.setForeground(Color.GRAY);
            customLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            infoPanel.add(customLabel);
        }

        JLabel priceLabel = new JLabel(String.format("%.2f TL × %d = %.2f TL",
                item.getUnitPrice(), item.getQuantity(), item.getTotalPrice()));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        priceLabel.setForeground(new Color(34, 139, 34));
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(priceLabel);

        // Quantity controls
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        controlPanel.setBackground(Color.WHITE);

        JButton minusButton = new JButton("-");
        minusButton.setFont(new Font("Arial", Font.BOLD, 20));
        minusButton.setPreferredSize(new Dimension(50, 40));
        minusButton.setBackground(new Color(220, 220, 220));
        minusButton.setFocusPainted(false);
        minusButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        minusButton.addActionListener(e -> {
            item.decrementQuantity();
            refreshCart();
        });

        JLabel qtyLabel = new JLabel(String.valueOf(item.getQuantity()));
        qtyLabel.setFont(new Font("Arial", Font.BOLD, 18));
        qtyLabel.setPreferredSize(new Dimension(40, 40));
        qtyLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton plusButton = new JButton("+");
        plusButton.setFont(new Font("Arial", Font.BOLD, 20));
        plusButton.setPreferredSize(new Dimension(50, 40));
        plusButton.setBackground(new Color(34, 139, 34));
        plusButton.setForeground(Color.WHITE);
        plusButton.setFocusPainted(false);
        plusButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        plusButton.addActionListener(e -> {
            item.incrementQuantity();
            refreshCart();
        });

        JButton removeButton = new JButton("🗑️");
        removeButton.setFont(new Font("Arial", Font.PLAIN, 20));
        removeButton.setPreferredSize(new Dimension(50, 40));
        removeButton.setBackground(new Color(220, 53, 69));
        removeButton.setForeground(Color.WHITE);
        removeButton.setFocusPainted(false);
        removeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        removeButton.addActionListener(e -> {
            mainFrame.getOrderController().removeFromCart(item);
            refreshCart();
        });

        controlPanel.add(minusButton);
        controlPanel.add(qtyLabel);
        controlPanel.add(plusButton);
        controlPanel.add(Box.createHorizontalStrut(10));
        controlPanel.add(removeButton);

        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.EAST);

        return panel;
    }

    private void updateTotal() {
        double total = mainFrame.getOrderController().getCartTotal();
        totalLabel.setText(String.format("Toplam: %.2f TL", total));
        checkoutButton.setEnabled(total > 0);
    }

    private void clearCart() {
        int result = JOptionPane.showConfirmDialog(this,
                "Sepetteki tüm ürünler silinecek. Emin misiniz?",
                "Sepeti Temizle",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            mainFrame.getOrderController().clearCart();
            refreshCart();
        }
    }
}